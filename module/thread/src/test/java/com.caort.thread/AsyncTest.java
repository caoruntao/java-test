package com.caort.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author reed
 * @date 2022/7/6 09:08
 */
public class AsyncTest {
    /**
     * 统筹方法：泡茶
     * 1.洗水壶(1m)，烧水(15m)，泡茶(1m，等茶叶)
     * 2.洗茶壶(1m)，洗茶杯(2m)，准备茶叶(1m)
     * 1和2中的流程都是串行的，1中的泡茶需要等2中的茶叶
     */
    @Test
    public void thread() throws InterruptedException {
        // 使用线程实现
        Thread t2 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "洗茶壶(1m)");
                TimeUnit.SECONDS.sleep(1L);
                System.out.println(Thread.currentThread().getName() + "洗茶杯(2m)");
                TimeUnit.SECONDS.sleep(2L);
                System.out.println(Thread.currentThread().getName() + "准备茶叶(1m)");
                TimeUnit.SECONDS.sleep(1L);
                System.out.println(Thread.currentThread().getName() + "龙井");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "洗水壶(1m)");
                TimeUnit.SECONDS.sleep(1L);
                System.out.println(Thread.currentThread().getName() + "烧水(15m)");
                TimeUnit.SECONDS.sleep(15L);
                // 等茶叶
                t2.join();
                System.out.println(Thread.currentThread().getName() + "拿到茶叶");
                System.out.println(Thread.currentThread().getName() + "泡茶(1m)");
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t1.join();
    }

    @Test
    public void threadPool() throws ExecutionException, InterruptedException {
        // 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> f1 = executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() +  "洗茶壶(1m)");
                TimeUnit.SECONDS.sleep(1L);
                System.out.println(Thread.currentThread().getName() +  "洗茶杯(2m)");
                TimeUnit.SECONDS.sleep(2L);
                System.out.println(Thread.currentThread().getName() +  "准备茶叶(1m)");
                TimeUnit.SECONDS.sleep(1L);
                System.out.println(Thread.currentThread().getName() + "龙井");
                return "龙井";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
        Future<?> f2 = executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "洗水壶(1m)");
                TimeUnit.SECONDS.sleep(1L);
                System.out.println(Thread.currentThread().getName() + "烧水(15m)");
                TimeUnit.SECONDS.sleep(15L);
                // 等茶叶
                String tea = f1.get();
                System.out.println(Thread.currentThread().getName() + "拿到茶叶" + tea);
                System.out.println(Thread.currentThread().getName() + "泡茶(1m)");
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        f2.get();
    }

    @Test
    public void completableFuture() throws ExecutionException, InterruptedException {
        // CompletableFuture
        CompletableFuture<String> cf1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "洗茶壶(1m)");
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            System.out.println(Thread.currentThread().getName() + "洗茶杯(2m)");
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            System.out.println(Thread.currentThread().getName() + "准备茶叶(1m)");
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenApply(p -> "龙井");
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "洗水壶(1m)");
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            System.out.println(Thread.currentThread().getName() + "烧水(15m)");
            try {
                TimeUnit.SECONDS.sleep(15L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenAcceptBoth(cf1, (p, tea) -> {
            System.out.println(Thread.currentThread().getName() + "拿到茶叶" + tea);
            System.out.println(Thread.currentThread().getName() + "泡茶(1m)");
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        cf2.get();
    }
}
