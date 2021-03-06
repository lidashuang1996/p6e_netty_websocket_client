package com.p6e.netty.websocket.client.actuator;

import com.p6e.netty.websocket.client.P6eWebSocketClient;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 实现 P6eActuator 处理类
 * 实现 P6eContextConverter 转换类
 * 接收服务端的消息转换为处理类处理（异步的实现）
 * @author LiDaShuang
 * @version 1.0.0
 */
public abstract class P6eActuatorAbstractAsync extends P6eBaseActuator {

    /**
     * 创建一个默认的线程池，大小为 30 个线程
     *
     * 所有的异步线程的共用一个线程池
     */
    protected static ThreadPoolExecutor threadPool = null;

    public static void init(ThreadPoolExecutor tp) {
        threadPool = tp;
    }

    @Override
    public void __onOpen__(P6eWebSocketClient websocket) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onOpen(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onClose__(P6eWebSocketClient websocket) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onClose(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onError__(P6eWebSocketClient websocket, Throwable throwable) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onOpen(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageText__(P6eWebSocketClient websocket, String message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessageText(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageBinary__(P6eWebSocketClient websocket, byte[] message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessageBinary(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessagePong__(P6eWebSocketClient websocket, byte[] message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessagePong(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessagePing__(P6eWebSocketClient websocket, byte[] message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessagePing(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageContinuation__(P6eWebSocketClient websocket, byte[] message) {
        if (threadPool == null || threadPool.isShutdown()) return;
        threadPool.execute(() -> {
            this.onMessageContinuation(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public String toType() {
        return "ASYNCHRONOUS_TYPE";
    }
}
