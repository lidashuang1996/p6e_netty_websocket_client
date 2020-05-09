package com.p6e.netty.websocket.client.actuator;

import com.p6e.netty.websocket.client.P6eWebSocketClient;

/**
 * 这是 Web Socket Client 执行器
 * @author LiDaShuang
 * @version 1.0.0
 */
public interface P6eActuator {

    /**
     * 连接成功的触发的事件
     * @param webSocket WebSocket 对象的封装
     */
    public void onOpen(P6eWebSocketClient webSocket);

    /**
     * 连接关闭的触发的事件
     * @param webSocket WebSocket 对象的封装
     */
    public void onClose(P6eWebSocketClient webSocket);

    /**
     * 连接错误的触发的事件
     * @param webSocket WebSocket 对象的封装
     * @param throwable 错误事件对象
     */
    public void onError(P6eWebSocketClient webSocket, Throwable throwable);

    /**
     * 触发文本消息的事件
     * @param webSocket WebSocket 对象的封装
     */
    public void onMessageText(P6eWebSocketClient webSocket, String message);

    /**
     * 触发二进制消息的事件
     * @param webSocket WebSocket 对象的封装
     * @param message 消息内容
     */
    public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message);

    /**
     * 触发 Pong 消息的事件
     * @param webSocket WebSocket 对象的封装
     * @param message 消息内容
     */
    public void onMessagePong(P6eWebSocketClient webSocket, byte[] message);

    /**
     * 触发 Ping 消息的事件
     * @param webSocket WebSocket 对象的封装
     * @param message 消息内容
     */
    public void onMessagePing(P6eWebSocketClient webSocket, byte[] message);

    /**
     * 触发 Continuation 消息的事件
     * @param webSocket WebSocket 对象的封装
     * @param message 消息内容
     */
    public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message);

}
