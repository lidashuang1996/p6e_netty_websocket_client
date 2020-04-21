package com.p6e.netty.websocket.client.mould;

import com.p6e.netty.websocket.client.product.P6eProduct;
import io.netty.channel.ChannelHandlerContext;

public class P6eMouldClient {
    private String id;
    private P6eProduct product;
    private P6eMouldClientCache cache;
    private ChannelHandlerContext channelHandlerContext;

    public P6eMouldClient() { }

    public P6eMouldClient(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public P6eMouldClient(P6eProduct product) {
        this.product = product;
    }

    public P6eMouldClient(P6eMouldClientCache cache) {
        this.cache = cache;
    }

    public P6eMouldClient(P6eProduct product, P6eMouldClientCache cache) {
        this.setProduct(product);
        this.cache = cache;
    }

    public P6eMouldClient(ChannelHandlerContext channelHandlerContext, P6eProduct product, P6eMouldClientCache cache) {
        this.cache = cache;
        this.product = product;
        this.channelHandlerContext = channelHandlerContext;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public P6eProduct getProduct() {
        return product;
    }

    public void setProduct(P6eProduct product) {
        this.product = product;
        this.id = product.getId();
    }

    public P6eMouldClientCache getCache() {
        return cache;
    }

    public void setCache(P6eMouldClientCache cache) {
        this.cache = cache;
    }

    public void removeCache() {
        cache.removeMouldClient(id);
    }
}
