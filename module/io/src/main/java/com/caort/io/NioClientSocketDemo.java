package com.caort.io;

import com.sun.security.jgss.GSSUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author Reed
 * @date 2021/5/26 上午8:40
 */
public class NioClientSocketDemo {
    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8090));
        byteBuffer.put("hello, world".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        System.out.println("write is finished");
        byteBuffer.clear();
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array()));
        byteBuffer.clear();
    }
}
