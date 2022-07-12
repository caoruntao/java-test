package com.caort.thread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reed
 * @date 2022/7/12 09:17
 */
public class WaitNotifyTest {

    @Test
    public void waitNotifyTest() throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        Allocator allocator = new Allocator();
        Thread thread = new Thread(() -> {
            allocator.apply(a, b);
            System.out.println(Thread.currentThread().getName() + "模拟执行");
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            allocator.free(a, b);
        });
        Thread thread1 = new Thread(() -> {
            allocator.apply(b, c);
            System.out.println(Thread.currentThread().getName() + "模拟执行");
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            allocator.free(b, c);
        });
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
    }

    static class Allocator {
        // 正在使用的资源
        private List<Object> usingResource = new ArrayList<>();

        synchronized public void apply(Object from, Object to) {
            // usingResource包含该对象表示资源已被占用，因此释放锁等待(Object#wait)资源释放时唤醒(Object#notify/Object#notifyAll)
            // 使用while循环而不是用if判断是因为线程被唤醒时(Object#notify/Object#notifyAll)条件是满足的，但是执行时(Object#wait返回时)条件不一定满足
            while (usingResource.contains(from) || usingResource.contains(to)) {
                try {
                    System.out.println(Thread.currentThread().getName() + "资源不满足，等待");
                    // wait释放锁后再次获取锁时从wait之后执行
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            usingResource.add(from);
            usingResource.add(to);
        }

        synchronized public void free(Object from, Object to) {
            usingResource.remove(from);
            usingResource.remove(to);
            // 如果没经过深思熟虑，最好不要使用Object#notify，因为可能导致线程不会被唤醒
            // 如果线程1，3需要资源A，线程2，4需要资源B，线程1和2已经获取了资源，线程3和4(wait)
            // 如果线程2执行完notify线程3，线程3因为资源A还没释放，继续wait，线程1执行完notify不管哪个，都会导致一个线程不会被唤醒
            System.out.println(Thread.currentThread().getName()  + "释放资源");
            notifyAll();
        }
    }
}
