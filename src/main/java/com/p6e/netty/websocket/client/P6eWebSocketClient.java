package com.p6e.netty.websocket.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.*;

/**
 * 对 Netty 的数据封装
 * 生成自定义的 WebSocketClient 对象
 * @author LiDaShuang
 * @version 1.0.0
 */
public class P6eWebSocketClient {
    private Channel channel;
    private String id;

    public P6eWebSocketClient(String id, Channel channel) {
        this.id = id;
        this.channel = channel;
    }

    public void sendTextMessage(String message) {
        this.channel.writeAndFlush(new TextWebSocketFrame(message));
    }

    public void sendBinaryMessage(byte[] message) {
        this.channel.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(message)));
    }

    public void sendPingMessage(byte[] message) {
        this.channel.writeAndFlush(new PingWebSocketFrame(Unpooled.wrappedBuffer(message)));
    }

    public void sendPongMessage(byte[] message) {
        this.channel.writeAndFlush(new PongWebSocketFrame(Unpooled.wrappedBuffer(message)));
    }

    public void sendContinuationMessage(byte[] message) {
        this.channel.writeAndFlush(new ContinuationWebSocketFrame(Unpooled.wrappedBuffer(message)));
    }

    public void close() {
        channel.close();
    }

    public boolean isOpen() {
        return channel.isOpen();
    }

    public boolean isActive() {
        return channel.isActive();
    }

    public boolean isRegistered() {
        return channel.isRegistered();
    }

    public boolean isWritable() {
        return channel.isWritable();
    }

    public Channel getChannel() {
        return channel;
    }

    public String getId() {
        return id;
    }
}
