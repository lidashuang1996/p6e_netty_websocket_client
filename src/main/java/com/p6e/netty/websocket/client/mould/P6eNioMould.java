package com.p6e.netty.websocket.client.mould;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class P6eNioMould extends P6eMould {

    @Override
    protected EventLoopGroup group(Bootstrap bootstrap) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup);
        return eventLoopGroup;
    }

    @Override
    public void channel(Bootstrap bootstrap) {
        bootstrap.channel(NioSocketChannel.class);
    }

}
