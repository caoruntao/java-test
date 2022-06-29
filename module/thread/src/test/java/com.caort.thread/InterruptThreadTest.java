package com.caort.thread;

import org.junit.jupiter.api.Test;

/**
 * @author reed
 * @date 2022/6/29 08:55
 */
public class InterruptThreadTest {

    @Test
    public void test1() {
        InnerThread innerThread = new InnerThread();
        innerThread.start();
        innerThread.interrupt();
    }

    public static class InnerThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(20000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 抛出InterruptedException后，中断标识会被重置
                System.out.println("中断标识:" + Thread.currentThread().isInterrupted());
            }
        }
    }


    @Test
    public void test2() {
        Object lock = new Object();
        InnerThread1 innerThread = new InnerThread1(lock);
        innerThread.start();
        innerThread.interrupt();
    }

    public static class InnerThread1 extends Thread {
        private final Object lock;

        public InnerThread1(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 抛出InterruptedException后，中断标识会被重置
                    System.out.println("中断标识:" + Thread.currentThread().isInterrupted());
                }
            }
        }
    }


    @Test
    public void test3() {
        Object lock = new Object();
        for (int i = 0; i < 5; i++) {
            InnerThread2 innerThread = new InnerThread2(lock);
            innerThread.start();
        }
    }

    public static class InnerThread2 extends Thread {
        private final Object lock;

        public InnerThread2(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                lock.notifyAll();
                try {
                    System.out.println(Thread.currentThread().getId() + "进入等待");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId() + "被唤醒");
                // 最后一个线程因为没人唤醒，因此在此添加唤醒
                lock.notifyAll();
            }
        }
    }
}
