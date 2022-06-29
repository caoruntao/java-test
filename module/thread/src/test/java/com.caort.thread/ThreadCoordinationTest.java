package com.caort.thread;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @author reed
 * @date 2022/6/29 13:39
 */
public class ThreadCoordinationTest {
    @Test
    public void countDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                countDownLatch.countDown();
                try {
                    System.out.println(Thread.currentThread().getId() + "等待其他线程ing");
                    countDownLatch.await();
                    System.out.println(Thread.currentThread().getId() + "执行ing");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Test
    public void cyclicBarrier() {
        // 相比于CountDownLatch，会自动复原
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getId() + "等待其他线程ing");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getId() + "执行ing");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Test
    public void semaphore() {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getId() + "开始处理");
                    // 模拟处理过程
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId() + "处理完成");
                semaphore.release();
            }).start();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
