package com.p6e.netty.websocket.client.instructions;

public interface P6eInstructions {

    public void onOpen(String id);

    public void onClose(String id);

    public void onError(String id, Throwable throwable);

    public void onMessageText(String id, String message);

    public void onMessageBinary(String id, byte[] bytes);

    public void onMessagePong(String id, byte[] bytes);

    public void onMessagePing(String id, byte[] bytes);

    public void onMessageContinuation(String id, byte[] bytes);

}
