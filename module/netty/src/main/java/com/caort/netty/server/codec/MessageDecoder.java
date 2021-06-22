package com.caort.netty.server.codec;

import com.caort.netty.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Reed
 * @date 2021/6/22 下午3:45
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.decode(byteBuf);

        out.add(requestMessage);
    }
}
