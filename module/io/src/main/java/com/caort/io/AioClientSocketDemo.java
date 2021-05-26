package com.caort.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * @author Reed
 * @date 2021/5/26 下午2:09
 */
public class AioClientSocketDemo {
    public static void main(String[] args) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        Future<Void> connect = socketChannel.connect(new InetSocketAddress(8090));
        connect.get();

        byteBuffer.put("hello, world".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();

        byteBuffer.clear();
        int len = 0;
        while ((len = socketChannel.read(byteBuffer).get()) != -1){
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array()));
            byteBuffer.clear();
        }
        socketChannel.close();
    }
}
