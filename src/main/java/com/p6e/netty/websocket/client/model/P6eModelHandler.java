package com.p6e.netty.websocket.client.model;

import com.p6e.netty.websocket.client.P6eWebSocketClient;
import com.p6e.netty.websocket.client.actuator.P6eActuatorDefault;
import com.p6e.netty.websocket.client.converter.P6eContextConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Netty 数据管道处理类
 * @author LiDaShuang
 * @version 1.0.0
 */
public class P6eModelHandler implements ChannelInboundHandler {

    /** 注入日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(P6eModelHandler.class);

    private volatile String id;
    private P6eModelCache p6eModelCache;
    private P6eContextConverter p6eContextConverter;
    private WebSocketClientHandshaker webSocketClientHandshaker;

    public P6eModelHandler(WebSocketClientHandshaker webSocketClientHandshaker,
                           P6eModelCache p6eModelCache, P6eContextConverter p6eContextConverter) {
        this.p6eModelCache = p6eModelCache;
        this.p6eContextConverter = p6eContextConverter == null ? new P6eActuatorDefault() : p6eContextConverter;
        this.webSocketClientHandshaker = webSocketClientHandshaker;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelRegistered ] ==> " + ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelUnregistered ] ==> " + ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        try {
            logger.debug("( " + id + " ) [ channelActive ] ==> " + ctx);
            webSocketClientHandshaker.handshake(ctx.channel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelInactive ] ==> " + ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.debug("( " + id + " ) [ channelRead ] ==> " + ctx);
        try {
            Channel channel = ctx.channel();
            if (!webSocketClientHandshaker.isHandshakeComplete()) { // 判断是否完成握手
                try {
                    webSocketClientHandshaker.finishHandshake(channel, (FullHttpResponse) msg); // 结束握手
                    id = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase(); // 客户端生成唯一ID
                    P6eWebSocketClient p6eWebSocketClient = new P6eWebSocketClient(id, ctx); // 创建 P6eWebSocketClient
                    p6eModelCache.put(id, p6eWebSocketClient);
                    if (id != null) p6eContextConverter.__onOpen__(p6eWebSocketClient); // 初始化
                    else logger.error("[ CLIENT ID ] ==> NULL.");
                } catch (WebSocketHandshakeException e) {
                    e.printStackTrace();
                }
            } else if (msg instanceof FullHttpResponse) { // 判断是否为 HTTP 返回的状态码
                FullHttpResponse response = (FullHttpResponse) msg;
                if (id != null) {
                    P6eWebSocketClient webSocket = p6eModelCache.get(id);
                    p6eModelCache.del(id);
                    p6eContextConverter.__onError__(webSocket,
                            new Exception("Unexpected FullHttpResponse [ "
                                    + response.status() + " ] ==> "
                                    + response.content().toString(CharsetUtil.UTF_8)));
                    p6eContextConverter.__onClose__(webSocket);
                } else logger.error("[ CLIENT ID ] ==> NULL.");
            } else {
                if (id != null) {
                    P6eWebSocketClient webSocket = p6eModelCache.get(id);
                    if (webSocket != null) {
                        WebSocketFrame frame = (WebSocketFrame) msg;
                        ByteBuf byteBuf = frame.content();
                        byte[] bytes = new byte[byteBuf.readableBytes()];
                        byteBuf.readBytes(bytes);
                        byteBuf.release();
                        if (frame instanceof BinaryWebSocketFrame) {
                            p6eContextConverter.__onMessageBinary__(webSocket, bytes);
                        } else if (frame instanceof TextWebSocketFrame) {
                            p6eContextConverter.__onMessageText__(webSocket, new String(bytes));
                        } else if (frame instanceof PongWebSocketFrame) {
                            p6eContextConverter.__onMessagePong__(webSocket, bytes);
                        } else if (frame instanceof PingWebSocketFrame) {
                            p6eContextConverter.__onMessagePing__(webSocket, bytes);
                        } else if (frame instanceof ContinuationWebSocketFrame) {
                            p6eContextConverter.__onMessageContinuation__(webSocket, bytes);
                        }
                    }
                } else logger.error("[ CLIENT ID ] ==> NULL.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelReadComplete ] ==> " + ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        logger.debug("( " + id + " ) [ userEventTriggered ] ==> " + ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ channelWritabilityChanged ] ==> " + ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ handlerAdded ] ==> " + ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        logger.debug("( " + id + " ) [ handlerRemoved ] ==> " + ctx);
        if (id != null) {
            p6eModelCache.del(id);
            p6eContextConverter.__onClose__(p6eModelCache.get(id));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.debug("( " + id + " ) [ exceptionCaught ] ==> " + ctx);
        cause.printStackTrace();
        if (id != null) p6eContextConverter.__onError__(p6eModelCache.get(id), cause);
        ctx.close(); // 关闭管道
    }

}
