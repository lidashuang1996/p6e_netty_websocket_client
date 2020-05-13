# p6e_netty_websocket_client



> p6e_netty_websocket_client 是使用 java 语言，采用 netty 框架，开发的一个 websocket client 应用。

## 1. 如何使用？

### 只需要 3 步即可

``` java
public class P6eWebSocketClientTest {

    /** 注入的日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eWebSocketClientTest.class);

    public static void main(String[] args)  {

        // 初始化日志对象
        P6eWebSocketClientLogger.init();

        // 1. 创建 application 对象
        P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run();

        // 2. 客户端 websocket 连接
        application.connect(
            new P6eConfig("WSS:// OR WS://", new P6eActuatorDefault())); // 同步默认的回调

        // 3. 关闭指定 ID 的客户端
        // application.close("");

        // 3. 关闭所有客户端
        // application.close();
        
        // 摧毁的方法
        // application.destroy();
    }
}
```

### 修改处理消息的模式

``` java
// 默认的实现
// P6eWebsocketClientApplication application 
// 				= P6eWebsocketClientApplication.run(P6eNioModel.class);

// 自定义的实现
P6eWebsocketClientApplication application 
    = P6eWebsocketClientApplication.run(new P6eModel() {
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
```

### 请求携带参数（header / cookies）

``` java
// 创建 application 对象
P6eWebsocketClientApplication application = P6eWebsocketClientApplication.run(P6eNioModel.class);
// 自定义 请求头 和 cookie
Map<String, Object> headers = new HashMap<>();
headers.put("h_name", "h_value");
// cookies 数据
List<P6eConfig.Cookie> cookies = new ArrayList<>();
cookies.add(new P6eConfig.Cookie("cookie_name", "cookie_value"));
// 连接服务
application.connect(new P6eConfig("WSS:// OR WS://", headers, cookies, new P6eActuatorDefault()));
```

### 回调函数同步处理

``` java
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
```

### 回调函数异步处理

``` java
// 线程池
ThreadPoolExecutor threadPool = 
    new ThreadPoolExecutor(0, 30, 60L, 
                           TimeUnit.SECONDS, new SynchronousQueue<>());

// 创建 application 对象
// threadPool 线程池用于当前 application 对象创建的异步消息处理
P6eWebsocketClientApplication application 
    = P6eWebsocketClientApplication.run(P6eNioModel.class, threadPool);

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

// 摧毁的方法
application.destroy();
```

## 2. 什么是 netty ？

> Netty是 *一个异步事件驱动的网络应用程序框架，*用于快速开发可维护的高性能协议服务器和客户端。
>
> 官网地址：[https://netty.io/](https://netty.io/)
>
> GitHub 地址： [https://github.com/netty/netty](https://github.com/netty/netty)

## 3. 什么是 websocket？

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

## 4. 依赖的架包

+ [io.netty_netty-all (4.1.48.Final)](https://github.com/netty/netty)
+ ch.qos.logback_logback-classic (1.2.3)

## 5. 使用该项目的有

+ [p6e_broadcast]( https://github.com/lidashuang1996/p6e_broadcast) 获取国内各大直播平台房间数据（弹幕数据，礼物数据 等）项目