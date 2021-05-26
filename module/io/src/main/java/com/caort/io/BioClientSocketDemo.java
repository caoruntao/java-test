package com.caort.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Reed
 * @date 2021/5/25 下午2:52
 */
public class BioClientSocketDemo {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            try (Socket socket = new Socket("127.0.0.1", 8090);
                 OutputStream outputStream = socket.getOutputStream();
                 InputStream inputStream = socket.getInputStream()){
                outputStream.write("hello, world".getBytes());
                socket.shutdownOutput();
                System.out.println("send is success");
                byte[] content = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(content)) != -1) {
                    System.out.println(new String(content));
                }
                System.out.println("socket is close");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
