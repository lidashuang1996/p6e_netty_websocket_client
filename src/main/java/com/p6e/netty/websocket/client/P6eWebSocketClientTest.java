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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class P6eWebSocketClientTest {

    /** 注入的日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eWebSocketClientTest.class);

    public static void main(String[] args)  {

        // 初始化日志对象
        P6eWebSocketClientLogger.init();

        // 1. 创建 application 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);

        P6eConfig p6eConfig = new P6eConfig("wss://wsproxy.douyu.com:6671/", new P6eActuatorDefault());
        p6eConfig.setSslPath("zhenshu.cer");
        // 2. 客户端 websocket 连接
        application.connect(p6eConfig); // 同步默认的回调

        // 3. 关闭指定 ID 的客户端
        // application.close("");

        // 3. 关闭所有客户端
        // application.close();

        // 4. 摧毁的方法
        // application.destroy();

    }

    public void test1() {
        // 默认的实现
        // P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);

        // 自定义的实现
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(new P6eModel() {
            @Override
            protected void option(Bootstrap bootstrap) {

            }

            @Override
            protected void channel(Bootstrap bootstrap) {

            }

            @Override
            protected EventLoopGroup group(Bootstrap bootstrap) {
                return null;
            }
        });
    }

    public void test2() {
        // 创建 application 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);

        // 自定义 请求头 和 cookie
        Map<String, Object> headers = new HashMap<>();
        headers.put("h_name", "h_value");
        // cookies 数据
        List<P6eConfig.Cookie> cookies = new ArrayList<>();
        cookies.add(new P6eConfig.Cookie("cookie_name", "cookie_value"));

        application.connect(new P6eConfig("WSS:// OR WS://", headers, cookies, new P6eActuatorDefault()));
    }

    public void test3() {
        // 创建 application 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);
        // 连接服务
        application.connect(new P6eConfig("WSS:// OR WS://", new P6eActuatorDefault()));

        // 连接服务
        application.connect(new P6eConfig("WSS:// OR WS://", new P6eActuatorDefault() {
            // 重写默认同步处理消息的方法
            // 自定义选择重写的回调函数
        }));

        // 连接服务
        application.connect(new P6eConfig("WSS:// OR WS://", new P6eActuatorAbstract() {
            // 实现同步抽象接口的方法
            @Override
            public void onOpen(P6eWebSocketClient webSocket) { }

            @Override
            public void onClose(P6eWebSocketClient webSocket) { }

            @Override
            public void onError(P6eWebSocketClient webSocket, Throwable throwable) { }

            @Override
            public void onMessageText(P6eWebSocketClient webSocket, String message) { }

            @Override
            public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessagePong(P6eWebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessagePing(P6eWebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message) { }
        }));
    }

    public void test4() {

        // 线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(0, 30,
                60L, TimeUnit.SECONDS, new SynchronousQueue<>());

        // 创建 application 对象
        // threadPool 线程池用于当前 application 对象创建的异步消息处理
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class, threadPool);

        // 连接服务
        application.connect(new P6eConfig("WSS:// OR WS://", new P6eActuatorDefaultAsync()));

        // 连接服务
        application.connect(new P6eConfig("WSS:// OR WS://", new P6eActuatorDefaultAsync() {
            // 重写默认异步处理消息的方法
            // 自定义选择重写的回调函数
        }));

        // 连接服务
        application.connect(new P6eConfig("WSS:// OR WS://", new P6eActuatorAbstractAsync() {
            // 实现异步抽象接口的方法
            @Override
            public void onOpen(P6eWebSocketClient webSocket) { }

            @Override
            public void onClose(P6eWebSocketClient webSocket) { }

            @Override
            public void onError(P6eWebSocketClient webSocket, Throwable throwable) { }

            @Override
            public void onMessageText(P6eWebSocketClient webSocket, String message) { }

            @Override
            public void onMessageBinary(P6eWebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessagePong(P6eWebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessagePing(P6eWebSocketClient webSocket, byte[] message) { }

            @Override
            public void onMessageContinuation(P6eWebSocketClient webSocket, byte[] message) { }
        }));

        // 4. 摧毁的方法
        application.destroy();
    }
}
