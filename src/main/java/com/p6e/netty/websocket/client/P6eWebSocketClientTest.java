package com.p6e.netty.websocket.client;

import com.p6e.netty.websocket.client.actuator.P6eActuatorAbstractAsync;
import com.p6e.netty.websocket.client.model.P6eModel;
import com.p6e.netty.websocket.client.model.P6eNioModel;
import com.p6e.netty.websocket.client.config.P6eConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class P6eWebSocketClientTest {

    private static final Logger logger = LoggerFactory.getLogger(P6eWebSocketClientTest.class);

    public static void main(String[] args)  {
        P6eWebSocketClientLogger.init();
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);
//        P6eWebsocketClientApplication application2 = P6eWebsocketClientApplication.run(new P6eModel() {
//            @Override
//            protected void option(Bootstrap bootstrap) {
//
//            }
//
//            @Override
//            protected void channel(Bootstrap bootstrap) {
//
//            }
//
//            @Override
//            protected EventLoopGroup group(Bootstrap bootstrap) {
//                return null;
//            }
//        });
        application.connect(new P6eConfig("ws://127.0.0.1:10000/ws", new P6eActuatorAbstractAsync() {
            @Override
            public void onOpen(P6eWebSocketClient webSocket) {
                logger.info("[ 1 ] ==> " + webSocket);
            }

            @Override
            public void onClose(P6eWebSocketClient webSocket) {
                logger.info("[ 2 ] ==> " + webSocket);
            }

            @Override
            public void onError(P6eWebSocketClient webSocket, Throwable throwable) {
                logger.info("[ 3 ] ==> " + webSocket + " throwable ==> " + throwable.getMessage());
            }

            @Override
            public void onMessageText(P6eWebSocketClient webSocket, String message) {
                logger.info("[ 4 ] ==> " + webSocket + " message ==> " + message);
            }

            @Override
            public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message) {
                logger.info("[ 5 ] ==> " + webSocket + " message ==> " + new String(message));
            }

            @Override
            public void onMessagePong(P6eWebSocketClient webSocket, byte[] message) {
                logger.info("[ 6 ] ==> " + webSocket + " message ==> " + new String(message));
            }

            @Override
            public void onMessagePing(P6eWebSocketClient webSocket, byte[] message) {
                logger.info("[ 7 ] ==> " + webSocket + " message ==> " + new String(message));
            }

            @Override
            public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message) {
                logger.info("[ 8 ] ==> " + webSocket + " message ==> " + new String(message));
            }
        }));
    }
}
