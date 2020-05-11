package com.p6e.netty.websocket.client.model;

import com.p6e.netty.websocket.client.P6eWebSocketClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 每一个模板对应一个缓存对象，用来缓存连接的客户端信息
 * @author LiDaShuang
 * @version 1.0.0
 */
public class P6eModelCache {

    private Map<String, P6eWebSocketClient> p6eMouldClientMap = new HashMap<>();

    public void put(String id, P6eWebSocketClient client) {
        if (id != null) p6eMouldClientMap.put(id, client);
    }

    public void del(String id) {
        if (id != null) p6eMouldClientMap.remove(id);
    }

    public P6eWebSocketClient get(String id) {
        if (id == null) return null;
        else return p6eMouldClientMap.get(id);
    }
    public Map<String, P6eWebSocketClient> get() {
        return p6eMouldClientMap;
    }

}
