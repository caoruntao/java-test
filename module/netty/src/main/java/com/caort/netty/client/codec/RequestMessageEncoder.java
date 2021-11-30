package com.caort.netty.client.codec;

import com.caort.netty.common.RequestMessage;
import com.caort.netty.common.order.OrderOperation;
import com.caort.netty.util.IdUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Caort.
 * @date 2021/6/22 下午3:51
 */
public class RequestMessageEncoder extends MessageToMessageEncoder<OrderOperation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, OrderOperation orderOperation, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();

        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), orderOperation);
        requestMessage.encode(buffer);

        out.add(buffer);
    }
}
