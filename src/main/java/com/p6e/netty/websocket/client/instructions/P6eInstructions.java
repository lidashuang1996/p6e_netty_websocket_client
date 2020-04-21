package com.p6e.netty.websocket.client.instructions;

import com.p6e.netty.websocket.client.mould.P6eMouldClient;

public abstract interface P6eInstructions {

    /**
     * 注入客户端对象的方法
     */
    public void __init__(P6eMouldClient client);

    public void onOpen(String id);

    public void onClose(String id);

    public void onError(String id, Throwable throwable);

    public void onMessageText(String id, String message);

    public void onMessageBinary(String id, byte[] bytes);

    public void onMessagePong(String id, byte[] bytes);

    public void onMessagePing(String id, byte[] bytes);

    public void onMessageContinuation(String id, byte[] bytes);

}
