package com.p6e.netty.websocket.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;

/**
 * 对 Netty 的数据封装
 * 生成自定义的 WebSocketClient 对象
 * @author LiDaShuang
 * @version 1.0.0
 */
public class P6eWebSocketClient {

    private String id;
    private ChannelHandlerContext channelHandlerContext;

    public P6eWebSocketClient(String id, ChannelHandlerContext channelHandlerContext) {
        this.id = id;
        this.channelHandlerContext = channelHandlerContext;
    }

    public void sendTextMessage(String message) {
        this.channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

    public void sendBinaryMessage(byte[] message) {
        this.channelHandlerContext.channel().writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(message)));
    }

    public void sendPingMessage(byte[] message) {
        this.channelHandlerContext.channel().writeAndFlush(new PingWebSocketFrame(Unpooled.wrappedBuffer(message)));
    }

    public void sendPongMessage(byte[] message) {
        this.channelHandlerContext.channel().writeAndFlush(new PongWebSocketFrame(Unpooled.wrappedBuffer(message)));
    }

    public void sendContinuationMessage(byte[] message) {
        this.channelHandlerContext.channel().writeAndFlush(new ContinuationWebSocketFrame(Unpooled.wrappedBuffer(message)));
    }

    public void close() {
        channelHandlerContext.channel().close();
    }

    public boolean isOpen() {
        return channelHandlerContext.channel().isOpen();
    }

    public boolean isActive() {
        return channelHandlerContext.channel().isActive();
    }

    public boolean isRegistered() {
        return channelHandlerContext.channel().isRegistered();
    }

    public boolean isWritable() {
        return channelHandlerContext.channel().isWritable();
    }

    public Channel getChannel() {
        return channelHandlerContext.channel();
    }

    public String getId() {
        return id;
    }
}
