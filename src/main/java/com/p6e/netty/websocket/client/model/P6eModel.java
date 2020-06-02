package com.p6e.netty.websocket.client.model;

import com.p6e.netty.websocket.client.P6eWebSocketClient;
import com.p6e.netty.websocket.client.config.P6eConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.*;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.*;
import java.util.List;
import java.util.Map;

/**
 * 创建 Netty 的 Web Socket Client 模型
 * @author LiDaShuang
 * @version 1.0.0
 */
public abstract class P6eModel {

    /** 日志注入对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eModel.class);

    /** Netty 的 Bootstrap */
    private Bootstrap bootstrap;

    /** Netty 的 EventLoopGroup */
    private EventLoopGroup eventLoopGroup;

    /** Netty 创建的 Web Socket Client 的缓存 */
    private P6eModelCache p6eModelCache;

    /**
     * 创建 P6eMould 实例时，创建该实例的 netty 的客户端
     */
    public P6eModel() {
        this.bootstrap = new Bootstrap();
        this.p6eModelCache = new P6eModelCache();
    }

    /**
     * 设置 Bootstrap 的 channel
     * @param bootstrap Bootstrap 对象
     */
    protected abstract void option(Bootstrap bootstrap);

    /**
     * 设置 Bootstrap 的 channel
     * @param bootstrap Bootstrap 对象
     */
    protected abstract void channel(Bootstrap bootstrap);

    /**
     * 设置 Bootstrap 的 EventLoopGroup
     * @param bootstrap Bootstrap 对象
     */
    protected abstract EventLoopGroup group(Bootstrap bootstrap);

    /**
     * 创建好执行的环境
     * @return P6eMould P6eModel 的对象
     */
    public P6eModel run() {
        try {
            this.option(this.bootstrap);
            this.bootstrap
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.AUTO_READ, true);
            this.channel(this.bootstrap);
            this.eventLoopGroup = this.group(this.bootstrap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 读取 WebSocket 配置信息连接服务端
     * @param config 配置信息
     * @return P6eMould 本身的对象
     */
    public P6eModel connect(final P6eConfig config) {
        try {
            P6eModelHandler p6eModelHandler = new P6eModelHandler(WebSocketClientHandshakerFactory.newHandshaker(
                    config.getUri(),
                    initWebSocketVersion(config.getVersion()),
                    null,
                    false,
                    initHttpHeaders(config)
            ), p6eModelCache, config.getActuator());
            this.bootstrap.handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws SSLException {
                    if (config.isNettyLoggerBool()) { // 是否开启 NETTY DEBUG 模式
                        LogLevel logLevel = initLogLevel(config.getNettyLogLevel());
                        if (logLevel != null) ch.pipeline().addLast(new LoggingHandler(logLevel));
                    }
                    // 是否为 WSS 协议的连接
                    if (P6eConfig.Agreement.WSS.toUpperCase().equals(config.getAgreement().toUpperCase())) {
                        if (config.getSslPath() == null) { // 不指定 SSL 证书的方式
                            SslContext sslContext = SslContextBuilder.forClient()
                                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                            ch.pipeline().addLast(sslContext.newHandler(ch.alloc()));
                        } else {
                            File file = new File(config.getSslPath());
                            if (!file.exists()) {
                                URL fileURL = this.getClass().getClassLoader().getResource(config.getSslPath());
                                if (fileURL != null) file = new File(fileURL.getFile());
                            }
                            if (!file.exists()) throw new RuntimeException(
                                    "certificate file not found. file path => " + config.getSslPath());
                            try { // 加载自定义的证书
                                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                                Certificate certificate = cf.generateCertificate(new FileInputStream(file));
                                String alias = ((X509Certificate) certificate).getSubjectDN().toString();
                                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                                keyStore.load(null, null);
                                keyStore.setCertificateEntry(alias, certificate);
                                TrustManagerFactory tmf =
                                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                                tmf.init(keyStore);
                                SslContext s = SslContextBuilder.forClient()
                                        .trustManager(tmf)
                                        .clientAuth(ClientAuth.NONE)
                                        .build();
                                ch.pipeline().addLast(s.newHandler(ch.alloc()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(8192));
                    ch.pipeline().addLast(p6eModelHandler);
                }
            });
            logger.debug("[ connect ] config ==> " + config);
            this.bootstrap.connect(config.getHost(), config.getPort()).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 读取 WebSocket 配置信息连接服务端
     * @param products 配置信息数组
     * @return P6eMould 本身的对象
     */
    public P6eModel connect(P6eConfig[] products) {
        for (P6eConfig product : products) connect(product);
        return this;
    }

    /**
     * 关闭所有的 WebSocket 客户端连接
     * 清除所全局的配置
     * @return P6eMould 本身的对象
     */
    public P6eModel close() {
        for (String key : p6eModelCache.get().keySet()) close(key);
        this.eventLoopGroup.shutdownGracefully();
        return this;
    }

    /**
     * 关闭 ID 指定客户端连接
     * @return P6eMould 本身的对象
     */
    public P6eModel close(String id) {
        P6eWebSocketClient p6eMouldClient = p6eModelCache.get(id);
        p6eModelCache.del(id);
        p6eMouldClient.close();
        return this;
    }

    /**
     * 关闭 ID 数组指定的客户端连接
     * @return P6eMould 本身的对象
     */
    public P6eModel close(String[] ids) {
        for (String id : ids) close(id);
        return this;
    }

    /**
     * 初始话请求的头部
     * @param config WebSocket 请求头部配置信息
     * @return HttpHeaders
     */
    private HttpHeaders initHttpHeaders(P6eConfig config) {
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        if (config != null) {
            Map<String, Object> headers = config.getHttpHeaders();
            if (headers != null) {
                for (String key : headers.keySet()) httpHeaders.add(key, headers.get(key));
            }
            List<P6eConfig.Cookie> cookies = config.getCookies();
            if (cookies != null && cookies.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (P6eConfig.Cookie cookie : cookies) sb.append("; ").append(cookie.content());
                httpHeaders.add("cookie", sb.substring(2));
            }
        }
        return httpHeaders;
    }

    /**
     * 初始化日志等级
     * @return 日志等级数据
     */
    private LogLevel initLogLevel(String logLevel) {
        logLevel = logLevel.toUpperCase();
        switch (logLevel) {
            case "TRACE":
                return LogLevel.TRACE;
            case "DEBUG":
                return LogLevel.DEBUG;
            case "WARN":
                return LogLevel.WARN;
            case "ERROR":
                return LogLevel.ERROR;
            case "INFO":
                return LogLevel.INFO;
            default:
                return null;
        }
    }

    /**
     * 初始化 Web Socket Version 版本号
     * @return WebSocketVersion 版本号对象
     */
    private WebSocketVersion initWebSocketVersion(String version) {
        version = version.toUpperCase();
        switch (version) {
            case "0":
            case "V0":
            case "V00":
                return WebSocketVersion.V00;
            case "7":
            case "V7":
            case "V07":
                return WebSocketVersion.V07;
            case "8":
            case "V8":
            case "V08":
                return WebSocketVersion.V08;
            case "13":
            case "V13":
                return WebSocketVersion.V13;
            default:
                return WebSocketVersion.UNKNOWN;
        }
    }

    /**
     * 返回当前对象连接的客户端数据
     * @return P6eModelCache 缓存的连接的客户端数据
     */
    public P6eModelCache getP6eModelCache() {
        return this.p6eModelCache;
    }
}
