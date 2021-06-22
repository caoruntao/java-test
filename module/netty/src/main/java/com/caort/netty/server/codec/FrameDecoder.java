package com.caort.netty.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 一次解码器，解决TCP沾包/半包问题
 *
 * @author Reed
 * @date 2021/6/22 下午3:09
 */
public class FrameDecoder extends LengthFieldBasedFrameDecoder {

    public FrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
