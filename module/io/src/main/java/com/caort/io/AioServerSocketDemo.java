package com.caort.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Reed
 * @date 2021/5/26 下午1:13
 */
public class AioServerSocketDemo {
    private ExecutorService executorService;
    private AsynchronousChannelGroup channelGroup;
    private AsynchronousServerSocketChannel serverSocketChannel;

    public AioServerSocketDemo() throws Exception {
        executorService = Executors.newCachedThreadPool();
        channelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1024);
        serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);
        serverSocketChannel.bind(new InetSocketAddress(8090));
        serverSocketChannel.accept(this, new ServerCompletionHandler());
    }

    public static void main(String[] args) throws Exception {
        new AioServerSocketDemo();
        Thread.sleep(Integer.MAX_VALUE);
    }

    static class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServerSocketDemo> {
        @Override
        public void completed(AsynchronousSocketChannel result, AioServerSocketDemo attachment) {
            attachment.serverSocketChannel.accept(attachment, this);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            result.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer resultSize, ByteBuffer attachment) {
                    attachment.flip();
                    String data = new String(attachment.array());
                    System.out.println("receive data : " + data);

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put(data.getBytes());
                    buffer.flip();
                    try {
                        result.write(buffer).get();
                        result.shutdownOutput();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
        }

        @Override
        public void failed(Throwable exc, AioServerSocketDemo attachment) {
            exc.printStackTrace();
        }
    }
}
