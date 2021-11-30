package com.caort.netty.client;

import com.caort.netty.client.codec.*;
import com.caort.netty.client.handler.DispatchHandler;
import com.caort.netty.common.RequestMessage;
import com.caort.netty.common.order.OrderOperation;
import com.caort.netty.dispatch.DispatchCenter;
import com.caort.netty.dispatch.OperationFuture;
import com.caort.netty.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 响应分发
 *
 * @author Caort.
 * @date 2021/6/22 下午4:19
 */
public class ClientV2 {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup work = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        DispatchCenter dispatchCenter = new DispatchCenter();
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new FrameDecoder());
                        p.addLast(new MessageDecoder());
                        p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new DispatchHandler(dispatchCenter));
                        p.addLast(new FrameEncoder());
                        p.addLast(new MessageEncoder());
                        p.addLast(new RequestMessageEncoder());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090).sync();

            long streamId = IdUtil.nextId();
            RequestMessage requestMessage = new RequestMessage(streamId, new OrderOperation(1001, "tudou"));
            OperationFuture operationFuture = new OperationFuture();
            dispatchCenter.add(streamId, operationFuture);

            channelFuture.channel().writeAndFlush(requestMessage);
            channelFuture.channel().closeFuture().sync();
        } finally {
            work.shutdownGracefully();
        }
    }
}
