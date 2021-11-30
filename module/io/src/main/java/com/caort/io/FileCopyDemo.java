package com.caort.io;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @author Caort.
 * @date 2021/5/25 上午11:07
 */
public class FileCopyDemo {
    public static void main(String[] args) throws IOException {
        String sourceFilePath = "/Users/trustasia/Downloads/10.19_002.mp4";
        String bioTargetFilePath = "/Users/trustasia/Downloads/10.19_002-bio.mp4";
        String bufferTargetFilePath = "/Users/trustasia/Downloads/10.19_002-buffer.mp4";
        String nioTargetFilePath = "/Users/trustasia/Downloads/10.19_002-nio.mp4";
        copyFileByBio(new File(sourceFilePath), new File(bioTargetFilePath));
        copyFileByBuffer(new File(sourceFilePath), new File(bufferTargetFilePath));
        copyFileByNio(new File(sourceFilePath), new File(nioTargetFilePath));
    }

    private static void copyFileByBio(File source, File target) throws IOException {
        long startTime = System.currentTimeMillis();
        try (FileInputStream inputStream = new FileInputStream(source);
             FileOutputStream outputStream = new FileOutputStream(target)) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, buffer.length);
            }
            outputStream.flush();
        }
        System.out.println("bio cost time : " + (System.currentTimeMillis() - startTime));
    }

    private static void copyFileByBuffer(File source, File target) throws IOException {
        long startTime = System.currentTimeMillis();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(target))) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer);
            }
            bufferedOutputStream.flush();
        }
        System.out.println("bio buffer cost time : " + (System.currentTimeMillis() - startTime));

    }

    private static void copyFileByNio(File source, File target) throws IOException {
        long startTime = System.currentTimeMillis();
        try (FileInputStream inputStream = new FileInputStream(source);
             FileOutputStream outputStream = new FileOutputStream(target);
             FileChannel sourceChannel = inputStream.getChannel();
             FileChannel targetChannel = outputStream.getChannel()) {
            sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        }
        System.out.println("nio cost time : " + (System.currentTimeMillis() - startTime));
    }
}
