package com.p6e.netty.websocket.client;

import com.p6e.netty.websocket.client.mould.P6eMould;
import com.p6e.netty.websocket.client.mould.P6eMouldClient;
import com.p6e.netty.websocket.client.mould.P6eMouldClientCache;
import com.p6e.netty.websocket.client.mould.P6eNioMould;
import com.p6e.netty.websocket.client.product.P6eProduct;

import java.util.Map;

public class P6eWebsocketClientApplication {
    private P6eMould mould;

    public static P6eWebsocketClientApplication run() {
        return new P6eWebsocketClientApplication(P6eNioMould.class, new P6eProduct[] {});
    }

    public static P6eWebsocketClientApplication run(Class<? extends P6eMould> mould) {
        return new P6eWebsocketClientApplication(mould, new P6eProduct[] {});
    }

    public static P6eWebsocketClientApplication run(Class<? extends P6eMould> mould, P6eProduct... products) {
        return new P6eWebsocketClientApplication(mould, products);
    }

    public P6eWebsocketClientApplication(Class<? extends P6eMould> mould, P6eProduct[] products) {
        try {
            this.mould = mould.newInstance().run().connect(products);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public P6eWebsocketClientApplication connect(P6eProduct product) {
        this.mould.connect(product);
        return this;
    }

    public P6eWebsocketClientApplication connect(P6eProduct[] products) {
        this.mould.connect(products);
        return this;
    }

    public P6eWebsocketClientApplication close() {
        this.mould.close();
        return this;
    }

    public P6eWebsocketClientApplication close(String id) {
        this.mould.close(id);
        return this;
    }

    public P6eWebsocketClientApplication close(String[] ids) {
        this.mould.close(ids);
        return this;
    }

    public P6eMouldClientCache getMouldClientCache() {
        return this.mould.getP6eMouldClientCache();
    }

    public P6eMouldClient getMouldClient(String id) {
        return this.mould.getMouldClient(id);
    }

    public Map<String, P6eMouldClient> getMouldClientMap() {
        return this.mould.getMouldClientMap();
    }

}
