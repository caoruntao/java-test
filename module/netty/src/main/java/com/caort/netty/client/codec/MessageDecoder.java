package com.caort.netty.client.codec;

import com.caort.netty.common.RequestMessage;
import com.caort.netty.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Reed
 * @date 2021/6/22 下午3:45
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(byteBuf);

        out.add(responseMessage);
    }
}
