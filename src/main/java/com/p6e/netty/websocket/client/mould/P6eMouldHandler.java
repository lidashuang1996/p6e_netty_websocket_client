package com.p6e.netty.websocket.client.mould;

import com.p6e.netty.websocket.client.instructions.P6eInstructionsDefault;
import com.p6e.netty.websocket.client.instructions.P6eInstructionsShell;
import com.p6e.netty.websocket.client.product.P6eProduct;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

public class P6eMouldHandler implements ChannelInboundHandler {

    private boolean isLog = false;

    private String id;
    private P6eInstructionsShell instructions;
    private ChannelPromise channelPromise;
    private P6eMouldClient p6eMouldClient;
    private WebSocketClientHandshaker webSocketClientHandshaker;

    public P6eMouldHandler(
            WebSocketClientHandshaker webSocketClientHandshaker,
            P6eMouldClient p6eMouldClient) {
        P6eProduct p6eProduct = p6eMouldClient.getProduct();
        if (p6eProduct == null) throw new RuntimeException("P6eMouldHandler.class p6eProduct null.");
        this.id = p6eProduct.getId();
        this.p6eMouldClient = p6eMouldClient;
        this.instructions = p6eProduct.getInstructions() == null
                ? new P6eInstructionsDefault() : p6eProduct.getInstructions();
        this.webSocketClientHandshaker = webSocketClientHandshaker;

        // 注入 client 对象
        this.instructions.__init__(p6eMouldClient);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        if (isLog) System.out.println(" ** channelRegistered ** ");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        if (isLog) System.out.println(" ** channelUnregistered ** ");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if (isLog) System.out.println(" ** channelActive ** ");

        webSocketClientHandshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (isLog) System.out.println(" ** channelInactive ** ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (isLog) System.out.println(" ** channelRead ** ");
        Channel channel = ctx.channel();
        // 写入最新的 ChannelHandlerContext
        p6eMouldClient.setChannelHandlerContext(ctx);
        if (!webSocketClientHandshaker.isHandshakeComplete()) {
            try {
                // 结束握手
                webSocketClientHandshaker.finishHandshake(channel, (FullHttpResponse) msg);
                channelPromise.setSuccess(); // 成功
                instructions.__onOpen__(id); // 初始化
            } catch (WebSocketHandshakeException e) {
                channelPromise.setFailure(e); // 关闭
            }
        } else if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            p6eMouldClient.removeCache();
            instructions.__onError__(id, new Exception("Unexpected FullHttpResponse [ "
                    + response.status() + " ] ==> " + response.content().toString(CharsetUtil.UTF_8)));
            instructions.__onClose__(id);
        } else {
            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof BinaryWebSocketFrame) {
                ByteBuf byteBuf = frame.content();
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                instructions.__onMessageBinary__(id, bytes);
            } else if (frame instanceof TextWebSocketFrame) {
                instructions.__onMessageText__(id, ((TextWebSocketFrame) frame).text());
            } else if (frame instanceof PongWebSocketFrame) {
                ByteBuf byteBuf = frame.content();
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                instructions.__onMessagePong__(id, bytes);
            } else if (frame instanceof PingWebSocketFrame) {
                ByteBuf byteBuf = frame.content();
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                instructions.__onMessagePing__(id, bytes);
            } else if (frame instanceof ContinuationWebSocketFrame) {
                ByteBuf byteBuf = frame.content();
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                instructions.__onMessageContinuation__(id, bytes);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        if (isLog) System.out.println(" ** channelReadComplete ** ");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (isLog) System.out.println(" ** userEventTriggered ** ");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        if (isLog) System.out.println(" ** channelWritabilityChanged ** ");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        if (isLog) System.out.println(" ** handlerAdded ** ");

        channelPromise = ctx.newPromise();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (isLog) System.out.println(" ** handlerRemoved ** ");

        p6eMouldClient.removeCache();
        instructions.__onClose__(id);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (isLog) System.out.println(" ** exceptionCaught ** ");

        instructions.__onError__(id, cause);
    }

    public ChannelPromise handshakeFuture() {
        return this.channelPromise;
    }
}
