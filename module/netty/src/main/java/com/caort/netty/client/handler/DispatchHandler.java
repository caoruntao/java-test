package com.caort.netty.client.handler;

import com.caort.netty.common.ResponseMessage;
import com.caort.netty.dispatch.DispatchCenter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Reed
 * @date 2021/7/2 上午9:16
 */
public class DispatchHandler extends SimpleChannelInboundHandler<ResponseMessage> {
    private DispatchCenter dispatchCenter;

    public DispatchHandler(DispatchCenter dispatchCenter) {
        this.dispatchCenter = dispatchCenter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMessage msg) throws Exception {
        dispatchCenter.result(msg.getMessageHeader().getStreamId(), msg);
    }
}
