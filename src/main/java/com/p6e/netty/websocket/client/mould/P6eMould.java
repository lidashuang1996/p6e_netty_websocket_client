package com.p6e.netty.websocket.client.mould;

import com.p6e.netty.websocket.client.product.P6eProduct;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.util.Map;

public abstract class P6eMould {

    private Bootstrap bootstrap;
    private EventLoopGroup eventLoopGroup;

    private P6eMouldClientCache p6eMouldClientCache;

    /**
     * 创建 P6eMould 实例时，创建该实例的 netty 的客户端
     */
    public P6eMould() {
        this.bootstrap = new Bootstrap();
        this.p6eMouldClientCache = new P6eMouldClientCache();
    }

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
     * @return P6eMould 本身的对象
     */
    public P6eMould run() {
        try {
            this.bootstrap
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            this.channel(this.bootstrap);
            this.eventLoopGroup = this.group(this.bootstrap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 读取 WebSocket 配置信息连接服务端
     * @param product 配置信息
     * @return P6eMould 本身的对象
     */
    public P6eMould connect(P6eProduct product) {
        try {
            P6eMouldClient p6eMouldClient = new P6eMouldClient(product, p6eMouldClientCache);
            P6eMouldHandler p6eMouldHandler = new P6eMouldHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(product.getUri(), WebSocketVersion.V13,
                            null, false, this.initHttpHeaders(product)), p6eMouldClient);

            this.bootstrap.handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws SSLException {
                    if ("WSS".equals(product.getAgreement().toUpperCase())) {
                        SslContext sslContext = SslContextBuilder.forClient()
                                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                        ch.pipeline().addLast(sslContext.newHandler(ch.alloc(), product.getHost(), product.getPort()));
                    }
                    ch.pipeline().addLast(new HttpClientCodec());
                    ch.pipeline().addLast(new HttpObjectAggregator(8192));
                    ch.pipeline().addLast("P6eMouldHandler", p6eMouldHandler);
                }
            });

            this.bootstrap.connect(product.getHost(), product.getPort()).sync();
            p6eMouldHandler.handshakeFuture().sync(); // 阻塞等待连接成功，然后才去连接下一个
            if (p6eMouldHandler.handshakeFuture().isSuccess()) {
                // 添加到缓存中
                this.p6eMouldClientCache.putMouldClient(product.getId(), p6eMouldClient);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 读取 WebSocket 配置信息连接服务端
     * @param products 配置信息数组
     * @return P6eMould 本身的对象
     */
    public P6eMould connect(P6eProduct[] products) {
        for (P6eProduct product : products) connect(product);
        return this;
    }

    /**
     * 关闭所有的 WebSocket 客户端连接
     * 清除所全局的配置
     * @return P6eMould 本身的对象
     */
    public P6eMould close() {
        for (String key : p6eMouldClientCache.getMouldClientMap().keySet()) close(key);
        this.eventLoopGroup.shutdownGracefully();
        return this;
    }

    /**
     * 关闭 ID 指定客户端连接
     * @return P6eMould 本身的对象
     */
    public P6eMould close(String id) {
        P6eMouldClient p6eMouldClient = p6eMouldClientCache.getMouldClientMap().get(id);
        p6eMouldClient.removeCache();
        p6eMouldClient.getChannelHandlerContext().close();
        return this;
    }

    /**
     * 关闭 ID 数组指定的客户端连接
     * @return P6eMould 本身的对象
     */
    public P6eMould close(String[] ids) {
        for (String id : ids) close(id);
        return this;
    }


    /**
     * 获取指定 ID 的 WebSocket 连接信息
     * @param id 指定的 WebSocket ID
     * @return 连接信息
     */
    public P6eMouldClient getMouldClient(String id) {
        return this.p6eMouldClientCache.getMouldClientMap().get(id);
    }

    /**
     * 获取所有的 WebSocket 连接信息
     * @return 连接信息
     */
    public Map<String, P6eMouldClient> getMouldClientMap() {
        return this.p6eMouldClientCache.getMouldClientMap();
    }

    /**
     * 初始话请求的头部
     * @param product WebSocket 配置信息
     * @return HttpHeaders
     */
    private HttpHeaders initHttpHeaders(P6eProduct product) {
        Map<String, Object> map = product.getHttpHeaders();
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        for (String key : map.keySet()) httpHeaders.add(key, map.get(key));
        return new DefaultHttpHeaders();
    }

    public P6eMouldClientCache getP6eMouldClientCache() {
        return p6eMouldClientCache;
    }
}
