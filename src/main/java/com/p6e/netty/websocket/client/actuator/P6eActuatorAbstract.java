package com.p6e.netty.websocket.client.actuator;

import com.p6e.netty.websocket.client.P6eWebSocketClient;

/**
 * 实现 P6eActuator 处理类
 * 实现 P6eContextConverter 转换类
 * 接收服务端的消息转换为处理类处理（同步的实现）
 * @author LiDaShuang
 * @version 1.0.0
 */
public abstract class P6eActuatorAbstract extends P6eBaseActuator {

    @Override
    public void __onOpen__(P6eWebSocketClient websocket) {
        this.onOpen(websocket);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onClose__(P6eWebSocketClient websocket) {
        this.onClose(websocket);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onError__(P6eWebSocketClient websocket, Throwable throwable) {
        this.onError(websocket, throwable);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessageText__(P6eWebSocketClient websocket, String message) {
        this.onMessageText(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessageBinary__(P6eWebSocketClient websocket, byte[] message) {
        this.onMessageBinary(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessagePong__(P6eWebSocketClient websocket, byte[] message) {
        this.onMessagePong(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessagePing__(P6eWebSocketClient websocket, byte[] message) {
        this.onMessagePing(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public void __onMessageContinuation__(P6eWebSocketClient websocket, byte[] message) {
        this.onMessageContinuation(websocket, message);
        System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
    }

    @Override
    public String toType() {
        return "SYNCHRONIZATION_TYPE";
    }
}
