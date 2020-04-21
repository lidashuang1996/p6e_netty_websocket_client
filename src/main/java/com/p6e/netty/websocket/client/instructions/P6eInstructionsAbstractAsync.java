package com.p6e.netty.websocket.client.instructions;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class P6eInstructionsAbstractAsync extends P6eInstructionsAbstract {

    protected ThreadPoolExecutor executor;

    public P6eInstructionsAbstractAsync() {
        this(10);
    }

    public P6eInstructionsAbstractAsync(int nThreads) {
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public void onOpen(String id) {
        executor.execute(() -> onOpenAsync(id));
    }

    @Override
    public void onClose(String id) {
        executor.execute(() -> onCloseAsync(id));
        executor.shutdown();
    }

    @Override
    public void onError(String id, Throwable throwable) {
        executor.execute(() -> onErrorAsync(id, throwable));
    }

    @Override
    public void onMessageText(String id, String message) {
        executor.execute(() -> onMessageTextAsync(id, message));
    }

    @Override
    public void onMessageBinary(String id, byte[] bytes) {
        executor.execute(() -> onMessageBinaryAsync(id, bytes));
    }

    @Override
    public void onMessagePong(String id, byte[] bytes) {
        executor.execute(() -> onMessagePongAsync(id, bytes));
    }

    @Override
    public void onMessagePing(String id, byte[] bytes) {
        executor.execute(() -> onMessagePingAsync(id, bytes));
    }

    @Override
    public void onMessageContinuation(String id, byte[] bytes) {
        executor.execute(() -> onMessageContinuationAsync(id, bytes));
    }


    public abstract void onOpenAsync(String id);
    public abstract void onCloseAsync(String id);
    public abstract void onErrorAsync(String id, Throwable throwable);
    public abstract void onMessageTextAsync(String id, String message);
    public abstract void onMessageBinaryAsync(String id, byte[] bytes);
    public abstract void onMessagePongAsync(String id, byte[] bytes);
    public abstract void onMessagePingAsync(String id, byte[] bytes);
    public abstract void onMessageContinuationAsync(String id, byte[] bytes);



}
