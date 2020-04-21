package com.p6e.netty.websocket.client.instructions;

public class P6eInstructionsDefault extends P6eInstructionsAbstract {

    @Override
    public void onOpen(String id) {
        System.out.println("[ " + id + " ] -- onOpen --");
    }

    @Override
    public void onMessageText(String id, String message) {
        System.out.println("[ " + id + " ] -- onMessageText --" + message);
    }

    @Override
    public void onMessageBinary(String id, byte[] bytes) {
        System.out.println("[ " + id + " ] -- onMessageBinary --" + new String(bytes));
    }

    @Override
    public void onMessagePong(String id, byte[] bytes) {
        System.out.println("[ " + id + " ] -- onMessagePong --" + new String(bytes));
    }

    @Override
    public void onMessagePing(String id, byte[] bytes) {
        System.out.println("[ " + id + " ] -- onMessagePing --" + new String(bytes));
    }

    @Override
    public void onMessageContinuation(String id, byte[] bytes) {
        System.out.println("[ " + id + " ] -- onMessageContinuation --" + new String(bytes));
    }

    @Override
    public void onClose(String id) {
        System.out.println("[ " + id + " ] -- onClose --");
    }

    @Override
    public void onError(String id, Throwable throwable) {
        System.out.println("[ " + id + " ] -- onError --" + throwable.getMessage());
    }
}
