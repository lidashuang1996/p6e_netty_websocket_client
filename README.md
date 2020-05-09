# p6e_netty_websocket_client



> p6e_netty_websocket_client 是使用 java 语言，采用 netty 框架，开发的一个 websocket client 版本。 

## 如何使用？

### 只需要 3 步即可

``` java
public class P6eWebSocketClientTest {

    /** 注入的日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eWebSocketClientTest.class);

    public static void main(String[] args)  {
        P6eWebSocketClientLogger.init(); // 初始化日志对象
        // 1. 创建 P6eWebsocketClientApplication 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);
        // 2. 连接 websocket 的地址
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws", new P6eActuatorAbstractAsync() {
            // 3. 对回调的事件进行处理
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

```

### 请求携带参数

``` java
public class P6eWebSocketClientTest {
    /** 注入的日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eWebSocketClientTest.class);
    public static void main(String[] args)  {
        P6eWebSocketClientLogger.init(); // 初始化日志对象
        // 1. 创建 P6eWebsocketClientApplication 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);
        // 2. 连接 websocket 的地址
        // web socket 只能通过 get 请求，然后升级协议
        // ?name=123 携带的参数可以传入到后台服务器
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123", new P6eActuatorDefault()));
    }
}
```

### 自定义请求头

``` java
public class P6eWebSocketClientTest {

    /** 注入的日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eWebSocketClientTest.class);

    public static void main(String[] args)  {
        P6eWebSocketClientLogger.init(); // 初始化日志对象
        // 1. 创建 P6eWebsocketClientApplication 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);
        // 2. 连接 websocket 的地址
        // 请求的请求头数据
        Map<String, Object> httpHeaders = new HashMap<>();
        httpHeaders.put("auth", "123456789");
        httpHeaders.put("name", "test");
        // 添加 地址 请求头 回调处理函数
        application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123", httpHeaders, new P6eActuatorDefault()));
    }
}
```



### 回调函数同步处理

``` java
// 请求的请求头数据
application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123", new P6eActuatorDefault())); // 同步默认的回调

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
```

### 回调函数异步处理

``` java
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
```

### 自定义 netty 的处理数据参数

``` java
P6eWebsocketClientApplication app = P6eWebsocketClientApplication.run(new P6eModel() {
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

// NIO 
public class P6eNioModel extends P6eModel {
    @Override
    protected EventLoopGroup group(Bootstrap bootstrap) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup);
        return eventLoopGroup;
    }
    @Override
    protected void option(Bootstrap bootstrap) {
    }
    @Override
    public void channel(Bootstrap bootstrap) {
        bootstrap.channel(NioSocketChannel.class);
    }
}
```

### 关闭和摧毁

``` java
// 关闭该 P6eWebsocketClientApplication 下面的所有连接 【不会关闭创建的领一个对象里面的连接】
application.close();
// 关闭该 P6eWebsocketClientApplication 下面的所有连接且如果采用了异步回调，摧毁该线程池 
//【不会摧毁创建的领一个对象里面的连接】
application.destroy();
```

### 关闭指定 ID 的客户端

``` java
// 关闭指定 ID 的客户端
// 如果该 ID 不是这个 application 创建的话
// 那么是删除不了这个指定 ID 的客户端
application.close("xxxx");
```

## 什么是 netty ？





## 什么是 websocket ？









