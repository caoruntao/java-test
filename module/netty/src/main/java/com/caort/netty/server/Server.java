package com.caort.netty.server;

import com.caort.netty.server.codec.FrameDecoder;
import com.caort.netty.server.codec.FrameEncoder;
import com.caort.netty.server.codec.MessageDecoder;
import com.caort.netty.server.codec.MessageEncoder;
import com.caort.netty.server.handler.ConnectionCountHandler;
import com.caort.netty.server.handler.IdleCheckHandler;
import com.caort.netty.server.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.flush.FlushConsolidationHandler;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * @author Caort.
 * @date 2021/6/22 下午3:08
 */
public class Server {
    public static void main(String[] args) throws InterruptedException, CertificateException, SSLException {
        UnorderedThreadPoolEventExecutor businessGroup = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business"));
        KQueueEventLoopGroup TSExecutor = new KQueueEventLoopGroup(0, new DefaultThreadFactory("TS"));
        ConnectionCountHandler connectionCountHandler = new ConnectionCountHandler();
        GlobalChannelTrafficShapingHandler TSHandler = new GlobalChannelTrafficShapingHandler(TSExecutor,
                100 * 1024, 100 * 1024, 20 * 1024, 20 * 1024, 1000, 15 * 1000);
        IpSubnetFilterRule ipSubnetFilterRule = new IpSubnetFilterRule("127.1.0.1", 16, IpFilterRuleType.REJECT);
        RuleBasedIpFilter ruleBasedIpFilter = new RuleBasedIpFilter(ipSubnetFilterRule);
        IdleCheckHandler idleCheckHandler = new IdleCheckHandler();
        SelfSignedCertificate cert = new SelfSignedCertificate();
        System.out.println(cert.certificate().getAbsoluteFile());
        SslContext sslContext = SslContextBuilder.forServer(cert.certificate(), cert.privateKey()).build();
        // 优化使用-native
        KQueueEventLoopGroup boss = new KQueueEventLoopGroup(new DefaultThreadFactory("boss"));
        KQueueEventLoopGroup work = new KQueueEventLoopGroup(new DefaultThreadFactory("work"));
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, work)
                .channel(KQueueServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        // 安全-ip黑白名单
                        p.addLast("ipFilter", ruleBasedIpFilter);
                        // 优化使用-流量整形
                        p.addLast("TSHandler", TSHandler);
                        // 跟踪诊断-内存泄漏  -Dio.netty.leakDetection.level=PARANOID
                        // 跟踪诊断-可视化-连接数统计
                        p.addLast("connectionCountHandler", connectionCountHandler);
                        // 安全-空闲监测
                        p.addLast("idleCheck", idleCheckHandler);
                        // 安全-SSL
                        p.addLast("ssl", sslContext.newHandler(ch.alloc()));
                        p.addLast(new FrameDecoder());
                        p.addLast(new MessageDecoder());
                        p.addLast(new FrameEncoder());
                        p.addLast(new MessageEncoder());

                        // 跟踪诊断-可诊断
                        p.addLast("loggingHandler", new LoggingHandler(LogLevel.INFO));
                        // 优化使用-增强写，提高吞吐量
                        p.addLast("flushEnhancer", new FlushConsolidationHandler(5, true));
                        // 优化使用-线程模型
                        p.addLast(businessGroup, new ServerHandler());
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
