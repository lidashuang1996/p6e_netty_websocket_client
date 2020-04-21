package com.p6e.netty.websocket.client.instructions;

public class P6eInstructionsDefaultAsync extends P6eInstructionsAbstractAsync {

    @Override
    public void onOpenAsync(String id) {
        System.out.println("[ " + id + " ] -- onOpenAsync --");
    }

    @Override
    public void onCloseAsync(String id) {
        System.out.println("[ " + id + " ] -- onCloseAsync --");
    }

    @Override
    public void onErrorAsync(String id, Throwable throwable) {
        System.out.println("[ " + id + " ] -- onErrorAsync --");
    }

    @Override
    public void onMessageTextAsync(String id, String message) {
        System.out.println("[ " + id + " ] -- onMessageTextAsync --" + message);
    }

    @Override
    public void onMessageBinaryAsync(String id, byte[] bytes) {
        System.out.println("[ " + id + " ] -- onMessageBinaryAsync --" + new String(bytes));
    }

    @Override
    public void onMessagePongAsync(String id, byte[] bytes) {
        System.out.println("[ " + id + " ] -- onMessagePongAsync --" + new String(bytes));
    }

    @Override
    public void onMessagePingAsync(String id, byte[] bytes) {
        System.out.println("[ " + id + " ] -- onMessagePingAsync --" + new String(bytes));
    }

    @Override
    public void onMessageContinuationAsync(String id, byte[] bytes) {
        System.out.println("[ " + id + " ] -- onMessageContinuationAsync --");
    }
}
