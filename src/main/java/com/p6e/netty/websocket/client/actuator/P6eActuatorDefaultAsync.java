package com.p6e.netty.websocket.client.actuator;

import com.p6e.netty.websocket.client.P6eWebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对异步处理器的默认实现
 * @author LiDaShuang
 * @version 1.0.0
 */
public class P6eActuatorDefaultAsync extends P6eActuatorAbstractAsync {

    /** 日志的对象的注入 */
    private static final Logger logger = LoggerFactory.getLogger(P6eActuatorDefaultAsync.class);

    @Override
    public void onOpen(P6eWebSocketClient webSocket) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onOpen.");
    }

    @Override
    public void onClose(P6eWebSocketClient webSocket) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onClose.");
    }

    @Override
    public void onError(P6eWebSocketClient webSocket, Throwable throwable) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onError. \n throwable => " + throwable.getMessage());
    }

    @Override
    public void onMessageText(P6eWebSocketClient webSocket, String message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessageText. \n message => " + message);
    }

    @Override
    public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessageBinary. \n message => " + new String(message));
    }

    @Override
    public void onMessagePong(P6eWebSocketClient webSocket, byte[] message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessagePong. \n message => " + new String(message));
    }

    @Override
    public void onMessagePing(P6eWebSocketClient webSocket, byte[] message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessagePing. \n message => " + new String(message));
    }

    @Override
    public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message) {
        logger.debug("WebSocket [ " + webSocket + " ] ==> onMessageContinuation. \n message => " + new String(message));
    }
}
