package com.caort.netty.server;

import com.caort.netty.server.codec.FrameDecoder;
import com.caort.netty.server.codec.FrameEncoder;
import com.caort.netty.server.codec.MessageDecoder;
import com.caort.netty.server.codec.MessageEncoder;
import com.caort.netty.server.handler.ConnectionCountHandler;
import com.caort.netty.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * @author Reed
 * @date 2021/6/22 下午3:08
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup(new DefaultThreadFactory("boss"));
        NioEventLoopGroup work = new NioEventLoopGroup(new DefaultThreadFactory("work"));
        ConnectionCountHandler connectionCountHandler = new ConnectionCountHandler();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast("connectionCountHandler", connectionCountHandler);

                        p.addLast(new FrameDecoder());
                        p.addLast(new MessageDecoder());
                        p.addLast(new FrameEncoder());
                        p.addLast(new MessageEncoder());

                        p.addLast("loggingHandler", new LoggingHandler(LogLevel.INFO));
                        p.addLast(new ServerHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(8090).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
