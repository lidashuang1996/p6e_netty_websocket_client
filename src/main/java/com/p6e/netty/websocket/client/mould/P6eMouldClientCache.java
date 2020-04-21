package com.p6e.netty.websocket.client.mould;

import java.util.Hashtable;
import java.util.Map;

/**
 * 缓存
 */
public class P6eMouldClientCache {

    private Map<String, P6eMouldClient> p6eMouldClientMap = new Hashtable<>();

    public void putMouldClient(String id, P6eMouldClient client) {
        p6eMouldClientMap.put(id, client);
    }

    public void removeMouldClient(String id) {
        p6eMouldClientMap.remove(id);
    }

    public Map<String, P6eMouldClient> getMouldClientMap() {
        return p6eMouldClientMap;
    }

}
