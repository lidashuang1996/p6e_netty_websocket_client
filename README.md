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

### 携带 Cookie

``` java
P6eWebSocketClientLogger.init(); // 初始化日志对象
// 1. 创建 P6eWebsocketClientApplication 对象
P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);
// 2. 连接 websocket 的地址
// 请求的请求头数据
Map<String, Object> map = new Hashtable<>();
map.put("name", "31232131");
// Cookis 的数据
List<P6eConfig.Cookie> cookies = new ArrayList<>();
cookies.add(new P6eConfig.Cookie("CookiesName", "CookiesValue"));
application.connect(new P6eConfig("ws://127.0.0.1:7510/ws?name=123",
                                  map,
                                  cookies,
                                  new P6eActuatorDefault())); // 同步默认的回调
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
// 关闭该 P6eWebsocketClientApplication 下面的所有连接 【不会关闭创建的另一个对象里面的连接】
application.close();
// 关闭该 P6eWebsocketClientApplication 下面的所有连接且如果采用了异步回调，摧毁该线程池 
//【不会摧毁创建的另一个对象里面的连接】
application.destroy();
```



### 关闭指定 ID 的客户端

``` java
// 关闭指定 ID 的客户端
// 如果该 ID 不是这个 application 创建的话
// 那么是删除不了这个指定 ID 的客户端
application.close("xxxx");
// 设置回调函数 获取客户端的 ID 
application.connect(new P6eConfig("ws://111.229.238.242:7510/ws?name=123",
                                  new P6eActuatorAbstractAsync() {
                                      @Override
                                      public void onOpen(P6eWebSocketClient webSocket) {
                                          // 通过 P6eWebSocketClient 对象获取 ID 的参数
                                          String id = webSocket.getId();
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
                                  }));
```

## 什么是 netty ？

> Netty是 *一个异步事件驱动的网络应用程序框架，*用于快速开发可维护的高性能协议服务器和客户端。
>
> 官网地址：[https://netty.io/](https://netty.io/)
>
> GitHub 地址： [https://github.com/netty/netty](https://github.com/netty/netty)

## 什么是 websocket？

> Web Socket 是 HTML5 一种新的协议。它实现了浏览器与服务器全双工通信 (full-duplex)。**一开始的握手需要借助HTTP请求完成**。

> Web Socket 复用了 HTTP 的握手通道。具体指的是，客户端通过 HTTP 请求与 Web Socket 服务端协商升级协议。协议升级完成后，后续的数据交换则遵照 Web Socket 的协议。
>
> 1. 客户端：申请协议升级
>
>    首先，客户端发起协议升级请求。可以看到，采用的是标准的HTTP报文格式，且只支持 **GET** 方法。
>
>    重点请求首部意义如下：
>
>    - Connection: Upgrade：表示要升级协议
>    - Upgrade: websocket：表示要升级到 websocket 协议。
>    - Sec-WebSocket-Version: 13：表示websocket的版本。如果服务端不支持该版本，需要返回一个Sec-WebSocket-Versionheader，里面包含服务端支持的版本号。
>    - Sec-WebSocket-Key：与后面服务端响应首部的 Sec-WebSocket-Accept 是配套的，提供基本的防护，比如恶意的连接，或者无意的连接。
>
>    注意，上面请求省略了部分非重点请求首部。由于是标准的HTTP请求，类似Host、Origin、Cookie等请求首部会照常发送。在握手阶段，可以通过相关请求首部进行 安全限制、权限校验等。
>
>    
>
> 2. 服务端：响应协议升级
>
>    服务端返回内容如下，状态代码101表示协议切换。到此完成协议升级，后续的数据交互都按照新的协议来。
>
>    每个 header 都以 rn 结尾，并且最后一行加上一个额外的空行 rn。此外，服务端回应的HTTP状态码只能在握手阶段使用。过了握手阶段后，就只能采用特定的错误码。
>
>    
>
> 3. Sec-WebSocket-Accept 的计算
>
>    Sec-WebSocket-Accept 根据客户端请求首部的Sec-WebSocket-Key计算出来.
>
>    计算公式为：
>
>    1. 将 Sec-WebSocket-Key 跟 258EAFA5-E914-47DA-95CA-C5AB0DC85B11 拼接。
>    2. 通过 SHA1 计算出摘要，并转成 base64 字符串。

## 依赖的架包

+ [io.netty_netty-all (4.1.48.Final)](https://github.com/netty/netty)
+ org.slf4j_slf4j-api (1.7.30)
+ ch.qos.logback_logback-classic (1.2.3)

## 使用该项目的有

+ [p6e_broadcast]( https://github.com/lidashuang1996/p6e_broadcast) 获取国内各大直播平台房间数据（弹幕数据，礼物数据 等）项目