package com.caort.netty.server.handler;

import com.caort.netty.common.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Reed
 * @date 2021/6/22 下午3:58
 */
public class ServerHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();
        OperationResult operationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        ctx.writeAndFlush(responseMessage);
    }
}
