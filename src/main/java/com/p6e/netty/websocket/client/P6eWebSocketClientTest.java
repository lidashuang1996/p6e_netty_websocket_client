package com.p6e.netty.websocket.client;

import com.p6e.netty.websocket.client.actuator.P6eActuatorAbstract;
import com.p6e.netty.websocket.client.actuator.P6eActuatorAbstractAsync;
import com.p6e.netty.websocket.client.actuator.P6eActuatorDefault;
import com.p6e.netty.websocket.client.actuator.P6eActuatorDefaultAsync;
import com.p6e.netty.websocket.client.model.P6eModel;
import com.p6e.netty.websocket.client.model.P6eNioModel;
import com.p6e.netty.websocket.client.config.P6eConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class P6eWebSocketClientTest {

    /** 注入的日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eWebSocketClientTest.class);

    public static void main(String[] args)  {
        P6eWebSocketClientLogger.init(); // 初始化日志对象
        // 1. 创建 P6eWebsocketClientApplication 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);

        // 2. 连接 websocket 的地址
        // 请求的请求头数据
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123",
                new P6eActuatorDefault())); // 同步默认的回调
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123",
                new P6eActuatorDefault() {
                    @Override
                    public void onOpen(P6eWebSocketClient webSocket) {
                        super.onOpen(webSocket);
                    }

                    @Override
                    public void onClose(P6eWebSocketClient webSocket) {
                        super.onClose(webSocket);
                    }

                    @Override
                    public void onError(P6eWebSocketClient webSocket, Throwable throwable) {
                        super.onError(webSocket, throwable);
                    }

                    @Override
                    public void onMessageText(P6eWebSocketClient webSocket, String message) {
                        super.onMessageText(webSocket, message);
                    }

                    @Override
                    public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message) {
                        super.onMessageBinary(webSocket, message);
                    }

                    @Override
                    public void onMessagePong(P6eWebSocketClient webSocket, byte[] message) {
                        super.onMessagePong(webSocket, message);
                    }

                    @Override
                    public void onMessagePing(P6eWebSocketClient webSocket, byte[] message) {
                        super.onMessagePing(webSocket, message);
                    }

                    @Override
                    public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message) {
                        super.onMessageContinuation(webSocket, message);
                    }
                })); // 同步默认的回调 - 继承的方式重写
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123",
                new P6eActuatorAbstract() {
                    @Override
                    public void onOpen(P6eWebSocketClient webSocket) {

                    }

                    @Override
                    public void onClose(P6eWebSocketClient webSocket) {

                    }

                    @Override
                    public void onError(P6eWebSocketClient webSocket, Throwable throwable) {

                    }

                    @Override
                    public void onMessageText(P6eWebSocketClient webSocket, String message) {

                    }

                    @Override
                    public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message) {

                    }

                    @Override
                    public void onMessagePong(P6eWebSocketClient webSocket, byte[] message) {

                    }

                    @Override
                    public void onMessagePing(P6eWebSocketClient webSocket, byte[] message) {

                    }

                    @Override
                    public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message) {

                    }
                })); // 抽象父类的方式重写

        application.close("xxxx");
        application.destroy();

        // 请求的请求头数据
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123",
                new P6eActuatorDefaultAsync())); // 异步默认的回调
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123",
                new P6eActuatorDefaultAsync() {
                    @Override
                    public void onOpen(P6eWebSocketClient webSocket) {
                        super.onOpen(webSocket);
                    }

                    @Override
                    public void onClose(P6eWebSocketClient webSocket) {
                        super.onClose(webSocket);
                    }

                    @Override
                    public void onError(P6eWebSocketClient webSocket, Throwable throwable) {
                        super.onError(webSocket, throwable);
                    }

                    @Override
                    public void onMessageText(P6eWebSocketClient webSocket, String message) {
                        super.onMessageText(webSocket, message);
                    }

                    @Override
                    public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message) {
                        super.onMessageBinary(webSocket, message);
                    }

                    @Override
                    public void onMessagePong(P6eWebSocketClient webSocket, byte[] message) {
                        super.onMessagePong(webSocket, message);
                    }

                    @Override
                    public void onMessagePing(P6eWebSocketClient webSocket, byte[] message) {
                        super.onMessagePing(webSocket, message);
                    }

                    @Override
                    public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message) {
                        super.onMessageContinuation(webSocket, message);
                    }
                })); // 异步默认的回调 - 继承的方式重写
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123",
                new P6eActuatorAbstractAsync() {
                    @Override
                    public void onOpen(P6eWebSocketClient webSocket) {

                    }

                    @Override
                    public void onClose(P6eWebSocketClient webSocket) {

                    }

                    @Override
                    public void onError(P6eWebSocketClient webSocket, Throwable throwable) {

                    }

                    @Override
                    public void onMessageText(P6eWebSocketClient webSocket, String message) {

                    }

                    @Override
                    public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message) {

                    }

                    @Override
                    public void onMessagePong(P6eWebSocketClient webSocket, byte[] message) {

                    }

                    @Override
                    public void onMessagePing(P6eWebSocketClient webSocket, byte[] message) {

                    }

                    @Override
                    public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message) {

                    }
                })); // 异步抽象父类的方式重写


    }
}
