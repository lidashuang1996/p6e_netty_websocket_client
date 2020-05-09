package com.p6e.netty.websocket.client.actuator;

import com.p6e.netty.websocket.client.P6eWebSocketClient;
import com.p6e.netty.websocket.client.converter.P6eContextConverter;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 实现 P6eActuator 处理类
 * 实现 P6eContextConverter 转换类
 * 接收服务端的消息转换为处理类处理（异步的实现）
 * @author LiDaShuang
 * @version 1.0.0
 */
public abstract class P6eActuatorAbstractAsync extends P6eBaseActuator implements P6eContextConverter, P6eActuator {

    /**
     * 创建一个默认的线程池，大小为 30 个线程
     */
    protected volatile static ThreadPoolExecutor executor = null;

    /**
     * 摧毁当前异步处理器的线程池
     */
    public synchronized static void destroyThreadPool() {
        if (executor != null) {
            if (!executor.isShutdown()) executor.shutdown();
            executor = null;
        }
    }

    public P6eActuatorAbstractAsync() {
        if (executor == null || executor.isShutdown()) {
            executor = new ThreadPoolExecutor(0, 30,
                    60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        }
    }

    public P6eActuatorAbstractAsync(ThreadPoolExecutor threadPool) {
        destroyThreadPool();
        executor = threadPool;
    }

    @Override
    public void __onOpen__(P6eWebSocketClient websocket) {
        if (executor == null || executor.isShutdown()) return;
        executor.execute(() -> {
            this.onOpen(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onClose__(P6eWebSocketClient websocket) {
        if (executor == null || executor.isShutdown()) return;
        executor.execute(() -> {
            this.onClose(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onError__(P6eWebSocketClient websocket, Throwable throwable) {
        if (executor == null || executor.isShutdown()) return;
        executor.execute(() -> {
            this.onOpen(websocket);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageText__(P6eWebSocketClient websocket, String message) {
        if (executor == null || executor.isShutdown()) return;
        executor.execute(() -> {
            this.onMessageText(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageBinary__(P6eWebSocketClient websocket, byte[] message) {
        if (executor == null || executor.isShutdown()) return;
        executor.execute(() -> {
            this.onMessageBinary(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessagePong__(P6eWebSocketClient websocket, byte[] message) {
        if (executor == null || executor.isShutdown()) return;
        executor.execute(() -> {
            this.onMessagePong(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessagePing__(P6eWebSocketClient websocket, byte[] message) {
        if (executor == null || executor.isShutdown()) return;
        executor.execute(() -> {
            this.onMessagePing(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public void __onMessageContinuation__(P6eWebSocketClient websocket, byte[] message) {
        if (executor == null || executor.isShutdown()) return;
        executor.execute(() -> {
            this.onMessageContinuation(websocket, message);
            System.gc(); // 回收掉生成的一次性的对象，避免内存泄漏
        });
    }

    @Override
    public String toType() {
        return "ASYNCHRONOUS_TYPE";
    }
}
