package com.caort.netty.client;

import com.caort.netty.client.codec.FrameDecoder;
import com.caort.netty.client.codec.FrameEncoder;
import com.caort.netty.client.codec.MessageDecoder;
import com.caort.netty.client.codec.MessageEncoder;
import com.caort.netty.client.handler.IdleCheckHandler;
import com.caort.netty.common.RequestMessage;
import com.caort.netty.common.order.OrderOperation;
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
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;

/**
 * @author Reed
 * @date 2021/6/22 下午4:19
 */
public class Client {
    public static void main(String[] args) throws InterruptedException, SSLException {
        SslContext sslContext = SslContextBuilder.forClient().build();
        IdleCheckHandler idleCheckHandler = new IdleCheckHandler();
        NioEventLoopGroup work = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast("idle", idleCheckHandler);
                        p.addLast(sslContext.newHandler(ch.alloc()));
                        p.addLast(new FrameDecoder());
                        p.addLast(new MessageDecoder());
                        p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new FrameEncoder());
                        p.addLast(new MessageEncoder());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090).sync();
            RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), new OrderOperation(1001, "tudou"));
            channelFuture.channel().writeAndFlush(requestMessage);
            channelFuture.channel().closeFuture().sync();
        } finally {
            work.shutdownGracefully();
        }
    }
}
