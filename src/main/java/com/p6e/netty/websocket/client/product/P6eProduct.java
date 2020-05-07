package com.p6e.netty.websocket.client.product;

import com.p6e.netty.websocket.client.instructions.P6eInstructionsShell;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class P6eProduct {

    /**
     * WebSocket 支持的协议类型
     */
    private class Agreement {
        private static final String WS = "ws";
        private static final String WSS = "wss";
    }

    // 端口
    private int port;
    // 生成的 ID
    private String id;
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
    // 回调函数
    private P6eInstructionsShell instructions;
    // 请求的头部
    private Map<String, Object> httpHeaders = new HashMap<>();

    public P6eProduct() {
        try {
            this.generateId();
            this.setUri(new URI("ws://127.0.0.1:10000"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eProduct(String url) {
        try {
            this.generateId();
            this.setUri(new URI(url));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eProduct(URI uri) {
        this.generateId();
        this.setUri(uri);
    }

    public P6eProduct(String id, String url) {
        try {
            this.setId(id);
            this.setUri(new URI(url));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eProduct(String id, URI url) {
        this.setId(id);
        this.setUri(url);
    }

    public P6eProduct(URI uri, Map<String, Object> httpHeaders) {
        this.generateId();
        this.setUri(uri);
        this.setHttpHeaders(httpHeaders);
    }

    public P6eProduct(String id, URI uri, Map<String, Object> httpHeaders) {
        this.setId(id);
        this.setUri(uri);
        this.setHttpHeaders(httpHeaders);
    }

    public P6eProduct(String url, Map<String, Object> httpHeaders) {
        try {
            this.generateId();
            this.setUri(new URI(url));
            this.setHttpHeaders(httpHeaders);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eProduct(String id, String url, Map<String, Object> httpHeaders) {
        try {
            this.setId(id);
            this.setUri(new URI(url));
            this.setHttpHeaders(httpHeaders);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eProduct(URI uri, P6eInstructionsShell instructions) {
        this.generateId();
        this.setUri(uri);
        this.setInstructions(instructions);
    }

    public P6eProduct(String id, URI uri, P6eInstructionsShell instructions) {
        this.setId(id);
        this.setUri(uri);
        this.setInstructions(instructions);
    }

    public P6eProduct(String url, P6eInstructionsShell instructions) {
        try {
            this.generateId();
            this.setUri(new URI(url));
            this.setInstructions(instructions);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eProduct(String id, String url, P6eInstructionsShell instructions) {
        try {
            this.setId(id);
            this.setUri(new URI(url));
            this.setInstructions(instructions);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eProduct(URI uri, Map<String, Object> httpHeaders, P6eInstructionsShell instructions) {
        this.generateId();
        this.setUri(uri);
        this.setHttpHeaders(httpHeaders);
        this.setInstructions(instructions);
    }

    public P6eProduct(String id, URI uri, Map<String, Object> httpHeaders, P6eInstructionsShell instructions) {
        this.setId(id);
        this.setUri(uri);
        this.setHttpHeaders(httpHeaders);
        this.setInstructions(instructions);
    }

    public P6eProduct(String url, Map<String, Object> httpHeaders, P6eInstructionsShell instructions) {
        try {
            this.generateId();
            this.setUri(new URI(url));
            this.setHttpHeaders(httpHeaders);
            this.setInstructions(instructions);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public P6eProduct(String id, String url, Map<String, Object> httpHeaders, P6eInstructionsShell instructions) {
        try {
            this.setId(id);
            this.setUri(new URI(url));
            this.setHttpHeaders(httpHeaders);
            this.setInstructions(instructions);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
        if (port < 0) {
            if (Agreement.WS.equals(this.getAgreement())) this.port = 80;
            else if (Agreement.WSS.equals(this.getAgreement())) this.port = 443;
            else {
                throw new RuntimeException("P6eProduct.class port parameter exception.");
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void generateId() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        if (host == null) throw new RuntimeException("P6eProduct.class host parameter exception.");
        this.host = host;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
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

    public P6eInstructionsShell getInstructions() {
        return instructions;
    }

    public void setInstructions(P6eInstructionsShell instructions) {
        this.instructions = instructions;
    }

    public Map<String, Object> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(Map<String, Object> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @Override
    public String toString() {
        return "{" + "\"port\":" +
                port +
                ",\"id\":\"" +
                id + '\"' +
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
                ",\"instructions\":" +
                instructions +
                ",\"httpHeaders\":" +
                httpHeaders +
                '}';
    }
}
