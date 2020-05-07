package com.p6e.netty.websocket.client.instructions;

import com.p6e.netty.websocket.client.mould.P6eMouldClient;

public interface P6eInstructionsShell {

    /**
     * 注入客户端对象的方法
     */
    public void __init__(P6eMouldClient client);

    public void __onOpen__(String id);

    public void __onClose__(String id);

    public void __onError__(String id, Throwable throwable);

    public void __onMessageText__(String id, String message);

    public void __onMessageBinary__(String id, byte[] bytes);

    public void __onMessagePong__(String id, byte[] bytes);

    public void __onMessagePing__(String id, byte[] bytes);

    public void __onMessageContinuation__(String id, byte[] bytes);

}
