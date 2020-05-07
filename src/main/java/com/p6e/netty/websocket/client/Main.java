package com.p6e.netty.websocket.client;

import com.p6e.netty.websocket.client.instructions.P6eInstructionsDefault;
import com.p6e.netty.websocket.client.instructions.P6eInstructionsDefaultAsync;
import com.p6e.netty.websocket.client.mould.P6eNioMould;
import com.p6e.netty.websocket.client.product.P6eProduct;

public class Main {
    public static void main(String[] args)  {
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioMould.class);
        application.connect(new P6eProduct("ws://127.0.0.1:10000/ws", new P6eInstructionsDefault() {
            @Override
            public void onMessageText(String id, String message) {
                System.out.println(" BIO  => " + id + "  MESSAGE => " + message);
                this.sendMessage("1", "11111111111111");
            }
        }));
//        application.connect(new P6eProduct("ws://127.0.0.1:10000/ws", new P6eInstructionsDefaultAsync() {
//            @Override
//            public void onMessageTextAsync(String id, String message) {
//                System.out.println(" NIO  => " + id + "  MESSAGE => " + message);
//                this.sendMessage("1", "22222222222222222222");
//            }
//        }));
    }
}
