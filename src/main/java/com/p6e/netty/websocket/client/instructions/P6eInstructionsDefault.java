package com.p6e.netty.websocket.client.instructions;

public class P6eInstructionsDefault extends P6eInstructionsAbstract {

    @Override
    public void onOpen(String id) {
    }

    @Override
    public void onMessageText(String id, String message) {
    }

    @Override
    public void onMessageBinary(String id, byte[] bytes) {
    }

    @Override
    public void onMessagePong(String id, byte[] bytes) {
    }

    @Override
    public void onMessagePing(String id, byte[] bytes) {
    }

    @Override
    public void onMessageContinuation(String id, byte[] bytes) {
    }

    @Override
    public void onClose(String id) {
    }

    @Override
    public void onError(String id, Throwable throwable) {
    }
}
