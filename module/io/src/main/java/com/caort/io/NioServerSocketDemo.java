package com.caort.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Caort.
 * @date 2021/5/25 下午5:13
 */
public class NioServerSocketDemo {
    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8090));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey next = selectionKeyIterator.next();
                selectionKeyIterator.remove();
                if (next.isValid()) {
                    if (next.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                        SocketChannel acceptSocket = channel.accept();
                        acceptSocket.configureBlocking(false);
                        acceptSocket.register(selector, SelectionKey.OP_READ);
                        System.out.println("accept is finished");
                    }
                    if (next.isReadable()) {
                        byteBuffer.clear();
                        SocketChannel channel = (SocketChannel) next.channel();
                        int len;
                        while((len = channel.read(byteBuffer)) != -1){
                            byteBuffer.flip();
                            System.out.println(new String(byteBuffer.array()));
                            channel.write(byteBuffer);
                            byteBuffer.clear();
                        }
                        channel.shutdownOutput();
                        System.out.println("write is finished");
                        channel.close();
                        next.cancel();
                    }
                }
            }
        }

    }
}
