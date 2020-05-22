package com.p6e.netty.websocket.client.config;

import com.p6e.netty.websocket.client.actuator.P6eBaseActuator;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 这是 Web Socket Client 配置文件的对象
 * @author LiDaShuang
 * @version 1.0.0
 */
public class P6eConfig {

    /**
     * 封装 WebSocket 支持的协议类型
     * @author LiDaShuang
     * @version 1.0.0
     */
    public final static class Agreement {
        public static final String WS = "ws";
        public static final String WSS = "wss";
    }

    /**
     * 封装 WebSocket 支持的版本号
     * @author LiDaShuang
     * @version 1.0.0
     */
    public final static class Version {
        public static final String V00 = "V00";
        public static final String V07 = "V07";
        public static final String V08 = "V08";
        public static final String V13 = "V13";
        public static final String UNKNOWN = "UNKNOWN";
    }

    /**
     * 封装 WebSocket 日志版本
     * @author LiDaShuang
     * @version 1.0.0
     */
    public final static class LogLevel {
        public static final String NONE = "NONE";
        public static final String TRACE = "TRACE";
        public static final String DEBUG = "DEBUG";
        public static final String INFO = "INFO";
        public static final String WARN = "WARN";
        public static final String ERROR = "ERROR";
    }

    /**
     * 封装 Cookies 的对象
     * @author LiDaShuang
     * @version 1.0.0
     */
    public static class Cookie {
        private String name;
        private String value;

        public Cookie(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String content() {
            return name + "=" + value;
        }
    }

    // 端口
    private int port;
    // 主机地址
    private String host;
    // 协议类型
    private String agreement;
    // 请求路径
    private String path;
    // 请求的参数
    private String param;
    // URI 地址
    private URI uri;
    // 处理器
    private P6eBaseActuator actuator;
    // 版本号
    private String version = Version.V13;
    // Netty 日志是否开启
    private boolean nettyLoggerBool = false;
    // Netty 日志的等级
    private String nettyLogLevel = LogLevel.NONE;

    // 请求的 Cookie
    private List<Cookie> cookies = new ArrayList<>();
    // 请求的头部
    private Map<String, Object> httpHeaders = new HashMap<>();

    // 是否自定义证书
    private String sslPath;

    public P6eConfig() {
        try {
            this.setUri(new URI("ws://127.0.0.1:10000"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eConfig(String url) {
        try {
            this.setUri(new URI(url));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eConfig(URI uri) {
        this.setUri(uri);
    }

    public P6eConfig(URI uri, Cookie... cookies) {
        this.setUri(uri);
        this.addCookies(cookies);
    }

    public P6eConfig(String url, Cookie... cookies) {
        try {
            this.setUri(new URI(url));
            this.addCookies(cookies);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eConfig(URI uri, Map<String, Object> httpHeaders) {
        this.setUri(uri);
        this.addHttpHeaders(httpHeaders);
    }

    public P6eConfig(String url, Map<String, Object> httpHeaders) {
        try {
            this.setUri(new URI(url));
            this.addHttpHeaders(httpHeaders);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eConfig(URI uri, P6eBaseActuator actuator) {
        this.setUri(uri);
        this.setActuator(actuator);
    }

    public P6eConfig(String url, P6eBaseActuator actuator) {
        try {
            this.setUri(new URI(url));
            this.setActuator(actuator);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eConfig(URI uri, Map<String, Object> httpHeaders, List<Cookie> cookies) {
        this.setUri(uri);
        this.addHttpHeaders(httpHeaders);
        this.addCookies(cookies);
    }

    public P6eConfig(String url, Map<String, Object> httpHeaders, List<Cookie> cookies) {
        try {
            this.setUri(new URI(url));
            this.addHttpHeaders(httpHeaders);
            this.addCookies(cookies);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eConfig(URI uri, Map<String, Object> httpHeaders, P6eBaseActuator actuator) {
        this.setUri(uri);
        this.addHttpHeaders(httpHeaders);
        this.setActuator(actuator);
    }

    public P6eConfig(String url, Map<String, Object> httpHeaders, P6eBaseActuator actuator) {
        try {
            this.setUri(new URI(url));
            this.addHttpHeaders(httpHeaders);
            this.setActuator(actuator);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eConfig(URI uri, List<Cookie> cookies, P6eBaseActuator actuator) {
        this.setUri(uri);
        this.addCookies(cookies);
        this.setActuator(actuator);
    }

    public P6eConfig(String url, List<Cookie> cookies, P6eBaseActuator actuator) {
        try {
            this.setUri(new URI(url));
            this.addCookies(cookies);
            this.setActuator(actuator);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eConfig(URI uri, Map<String, Object> httpHeaders, List<Cookie> cookies, P6eBaseActuator actuator) {
        this.setUri(uri);
        this.addHttpHeaders(httpHeaders);
        this.addCookies(cookies);
        this.setActuator(actuator);
    }

    public P6eConfig(String url, Map<String, Object> httpHeaders, List<Cookie> cookies, P6eBaseActuator actuator) {
        try {
            this.setUri(new URI(url));
            this.addHttpHeaders(httpHeaders);
            this.addCookies(cookies);
            this.setActuator(actuator);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public int getPort() {
        return port;
    }

    private void setPort(int port) {
        this.port = port;
        if (port < 0) {
            if (Agreement.WS.equals(this.getAgreement())) this.port = 80;
            else if (Agreement.WSS.equals(this.getAgreement())) this.port = 443;
            else {
                throw new RuntimeException(this.getClass().toString() + " port parameter exception.");
            }
        }
    }

    public String getHost() {
        return host;
    }

    private void setHost(String host) {
        if (host == null) throw new RuntimeException(this.getClass().toString() + " host parameter exception.");
        this.host = host;
    }

    public String getAgreement() {
        return agreement;
    }

    private void setAgreement(String agreement) {
        if (agreement == null) throw new RuntimeException(this.getClass().toString() + " agreement parameter exception.");
        this.agreement = agreement;
    }

    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public String getParam() {
        return param;
    }

    private void setParam(String param) {
        this.param = param == null ? "" : param;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
        this.setAgreement(uri.getScheme());
        this.setHost(uri.getHost());
        this.setPort(uri.getPort());
        this.setParam(uri.getQuery());
        this.setPath(uri.getRawPath());
    }

    public P6eBaseActuator getActuator() {
        return actuator;
    }

    public void setActuator(P6eBaseActuator actuator) {
        this.actuator = actuator;
    }

    public Map<String, Object> getHttpHeaders() {
        return httpHeaders;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void addCookies(Cookie... cookies) {
        this.cookies.addAll(Arrays.asList(cookies));
    }

    public void addCookies(List<Cookie> cookies) {
        this.cookies.addAll(cookies);
    }

    public void clearCookies() {
        this.cookies.clear();
    }

    public void addHttpHeaders(Map<String, Object> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public void delHttpHeaders(String headerName) {
        this.httpHeaders.remove(headerName);
    }

    public void clearHttpHeaders() {
        this.httpHeaders.clear();
    }

    public boolean isNettyLoggerBool() {
        return nettyLoggerBool;
    }

    public void setNettyLoggerBool(boolean nettyLoggerBool) {
        this.nettyLoggerBool = nettyLoggerBool;
    }

    public String getNettyLogLevel() {
        return nettyLogLevel;
    }

    public void setNettyLogLevel(String nettyLogLevel) {
        this.nettyLogLevel = nettyLogLevel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSslPath() {
        return sslPath;
    }

    public void setSslPath(String sslPath) {
        this.sslPath = sslPath;
    }

    @Override
    public String toString() {
        return "{" + "\"port\":" +
                port +
                ",\"host\":\"" +
                host + '\"' +
                ",\"agreement\":\"" +
                agreement + '\"' +
                ",\"path\":\"" +
                path + '\"' +
                ",\"param\":\"" +
                param + '\"' +
                ",\"uri\":" +
                uri +
                ",\"actuator\":" +
                actuator +
                ",\"version\":\"" +
                version + '\"' +
                ",\"nettyLoggerBool\":" +
                nettyLoggerBool +
                ",\"nettyLogLevel\":\"" +
                nettyLogLevel + '\"' +
                ",\"cookies\":" +
                cookies +
                ",\"httpHeaders\":" +
                httpHeaders +
                ",\"sslPath\":\"" +
                sslPath + '\"' +
                '}';
    }
}
