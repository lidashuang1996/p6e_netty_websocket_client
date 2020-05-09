package com.p6e.netty.websocket.client.converter;

import com.p6e.netty.websocket.client.P6eWebSocketClient;

/**
 * 上下文转换器
 * 将 Netty 触发的事件转换触发自定义 Web Socket Client 事件
 * @author LiDaShuang
 * @version 1.0.0
 */
public interface P6eContextConverter {

    /**
     * 连接触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onOpen__(P6eWebSocketClient websocket);

    /**
     * 关闭触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onClose__(P6eWebSocketClient websocket);

    /**
     * 错误触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onError__(P6eWebSocketClient websocket, Throwable throwable);

    /**
     * 文本消息触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessageText__(P6eWebSocketClient websocket, String message);

    /**
     * 二进制流消息触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessageBinary__(P6eWebSocketClient websocket, byte[] message);

    /**
     * Pong 消息触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessagePong__(P6eWebSocketClient websocket, byte[] message);

    /**
     * Ping 消息连接触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessagePing__(P6eWebSocketClient websocket, byte[] message);

    /**
     * Continuation 消息触发的回调
     * @param websocket 对 Netty 封装的对象
     */
    public void __onMessageContinuation__(P6eWebSocketClient websocket, byte[] message);

}
