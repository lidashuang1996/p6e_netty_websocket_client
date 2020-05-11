package com.p6e.netty.websocket.client;

import com.p6e.netty.websocket.client.actuator.P6eActuatorDefault;
import com.p6e.netty.websocket.client.model.P6eNioModel;
import com.p6e.netty.websocket.client.config.P6eConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class P6eWebSocketClientTest {

    /** 注入的日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eWebSocketClientTest.class);

    public static void main(String[] args)  {
        P6eWebSocketClientLogger.init(); // 初始化日志对象
        // 1. 创建 P6eWebsocketClientApplication 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);

//        // 2. 连接 websocket 的地址
//        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws",
//                new P6eActuatorDefault()));

        // 请求的请求头数据
        application.connect(new P6eConfig("wss://danmuproxy.douyu.com:8506/",
                new P6eActuatorDefault())); // 同步默认的回调

    }
}
