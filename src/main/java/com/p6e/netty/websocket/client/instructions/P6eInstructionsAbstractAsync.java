package com.p6e.netty.websocket.client.instructions;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class P6eInstructionsAbstractAsync
        extends P6eInstructionsAbstract implements P6eInstructionsShell, P6eInstructions  {

    protected final static ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    public P6eInstructionsAbstractAsync() {
        this(10);
    }

    public P6eInstructionsAbstractAsync(int nThreads) {
    }

    @Override
    public void __onOpen__(String id) {
        executor.execute(() -> {
            this.onOpen(id);
            System.gc();
        });
    }

    @Override
    public void __onClose__(String id) {
        executor.execute(() -> {
            this.onClose(id);
            System.gc();
        });
    }

    @Override
    public void __onError__(String id, Throwable throwable) {
        executor.execute(() -> {
            this.onOpen(id);
            System.gc();
        });
    }

    @Override
    public void __onMessageText__(String id, String message) {
        executor.execute(() -> {
            this.onMessageText(id, message);
            System.gc();
        });
    }

    @Override
    public void __onMessageBinary__(String id, byte[] bytes) {
        executor.execute(() -> {
            this.onMessageBinary(id, bytes);
            System.gc();
        });
    }

    @Override
    public void __onMessagePong__(String id, byte[] bytes) {
        executor.execute(() -> {
            this.onMessagePong(id, bytes);
            System.gc();
        });
    }

    @Override
    public void __onMessagePing__(String id, byte[] bytes) {
        executor.execute(() -> {
            this.onMessagePing(id, bytes);
            System.gc();
        });
    }

    @Override
    public void __onMessageContinuation__(String id, byte[] bytes) {
        executor.execute(() -> {
            this.onMessageContinuation(id, bytes);
            System.gc();
        });
    }
}
