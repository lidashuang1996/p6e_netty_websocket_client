package com.p6e.netty.websocket.client.instructions;

import com.p6e.netty.websocket.client.mould.P6eMouldClient;
import com.p6e.netty.websocket.client.product.P6eProduct;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.*;

public abstract class P6eInstructionsAbstract implements P6eInstructions {

    public static final String TEXT_MESSAGE_TYPE = "1";
    public static final String BINARY_MESSAGE_TYPE = "2";
    public static final String PONG_MESSAGE_TYPE = "3";
    public static final String PING_MESSAGE_TYPE = "4";

    protected String id;
    protected P6eProduct product;

    private P6eMouldClient client;

    @Override
    public void __init__(P6eMouldClient client) {
        this.client = client;
        this.product = client.getProduct();
        this.id = client.getProduct().getId();
    }

    /**
     * WebSocket 客户顿发送消息的功能
     * @param type 消息的类型
     * @param content 消息的内容
     */
    public synchronized void sendMessage(String type, Object content) {
        if (content == null) return;
        WebSocketFrame webSocketFrame = null;
        switch (type) {
            case TEXT_MESSAGE_TYPE:
                if (content instanceof String)
                    webSocketFrame = new TextWebSocketFrame((String) content);
                else throw new RuntimeException("TEXT_MESSAGE_TYPE message type error.");
                break;
            case BINARY_MESSAGE_TYPE:
                if (content instanceof byte[])
                    webSocketFrame = new BinaryWebSocketFrame(Unpooled.wrappedBuffer((byte[]) content));
                else throw new RuntimeException("BINARY_MESSAGE_TYPE message type error.");
                break;
            case PONG_MESSAGE_TYPE:
                if (content instanceof byte[])
                    webSocketFrame = new PongWebSocketFrame(Unpooled.wrappedBuffer((byte[]) content));
                else throw new RuntimeException("PONG_MESSAGE_TYPE message type error.");
                break;
            case PING_MESSAGE_TYPE:
                if (content instanceof byte[])
                    webSocketFrame = new PingWebSocketFrame(Unpooled.wrappedBuffer((byte[]) content));
                else throw new RuntimeException("PING_MESSAGE_TYPE message type error.");
                break;
            default:
                break;
        }
        if (webSocketFrame != null) {
            // client.getChannelHandlerContext() 实时更新的动态获取的
            client.getChannelHandlerContext().channel().writeAndFlush(webSocketFrame.retain());
        }
    }
}
