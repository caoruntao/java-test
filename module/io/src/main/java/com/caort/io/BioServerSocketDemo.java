package com.caort.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Caort.
 * @date 2021/5/25 下午2:11
 */
public class BioServerSocketDemo {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8090)) {
            while (true) {
                Socket accept = serverSocket.accept();
                new Thread(new MySocketHandler(accept)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MySocketHandler implements Runnable {
        private Socket socket;

        public MySocketHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.printf("Thread[%s] deal socket.\n", Thread.currentThread().getName());
            try (InputStream inputStream = socket.getInputStream();
                 OutputStream outputStream = socket.getOutputStream()) {
                byte[] content = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(content)) != -1) {
                    outputStream.write(content);
                    System.out.println(new String(content));
                }
                socket.shutdownOutput();
                System.out.printf("Thread[%s] deal socket is finished.\n", Thread.currentThread().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                    System.out.printf("Thread[%s] socket is close.\n", Thread.currentThread().getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }

        }
    }
}
