package com.caort.netty.server.codec;

import com.caort.netty.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Reed
 * @date 2021/6/22 下午3:51
 */
public class  MessageEncoder extends MessageToMessageEncoder<ResponseMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMessage responseMessage, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        responseMessage.encode(buffer);

        out.add(buffer);
    }
}
