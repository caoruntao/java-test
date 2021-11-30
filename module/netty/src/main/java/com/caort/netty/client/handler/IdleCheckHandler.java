package com.caort.netty.client.handler;

import com.caort.netty.common.RequestMessage;
import com.caort.netty.common.keepalive.KeepaliveOperation;
import com.caort.netty.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Caort.
 * @date 2021/7/6 下午1:27
 */
public class IdleCheckHandler extends IdleStateHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(IdleCheckHandler.class);
    public IdleCheckHandler() {
        super(0, 5, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if(evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT){
            logger.info("client write idle, send keepalive");
            RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), new KeepaliveOperation());
            ctx.writeAndFlush(requestMessage);
            return;
        }
        super.channelIdle(ctx, evt);
    }
}
