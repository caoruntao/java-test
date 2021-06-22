package com.caort.netty.server.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 一次编码器，解决TCP沾包/半包问题
 *
 * @author Reed
 * @date 2021/6/22 下午3:36
 */
public class FrameEncoder extends LengthFieldPrepender {

    public FrameEncoder() {
        super(2);
    }
}
