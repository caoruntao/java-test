package com.caort.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author reed
 * @date 2022/7/27 08:45
 */
public class RateLimiterTest {
    @Test
    public void limiter() {
        RateLimiter rateLimiter = new RateLimiter(5);
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch countDownLatch = new CountDownLatch(30);
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                rateLimiter.acquire();
                System.out.println(Thread.currentThread().getName() + "开始执行:" + System.currentTimeMillis());
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void limiterPlus() {
        RateLimiterPlus rateLimiter = new RateLimiterPlus(4, 2);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(30);
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                rateLimiter.acquire();
                System.out.println(Thread.currentThread().getName() + "开始执行:" + System.currentTimeMillis());
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模仿guava RateLimeter实现
     * 令牌桶算法，通过计算下次令牌生成时间来生成令牌。不使用定时器实现是因为高负荷下定时器不准确。
     */
    public static class RateLimiter {
        private long next = System.nanoTime();
        // 生成令牌的间隔时间，单位纳秒
        private final long interval;

        public RateLimiter(int perSecondCount) {
            // 设置令牌生成的间隔，默认桶容量为1
            this.interval = TimeUnit.SECONDS.toNanos(1L) / perSecondCount;
        }

        private synchronized long reserve(long now) {
            // 获取令牌时间在生成令牌时间之后
            if (now > next) {
                next = now;
            }
            long at = next;
            next += interval;
            // TODO 看有的地方返回Math.max(at, 0)，不明白什么意思，at有可能为负数吗
            return at;
        }

        private void acquire() {
            long now = System.nanoTime();
            long at = reserve(now);
            if (at > now) {
                try {
                    TimeUnit.NANOSECONDS.sleep(at - now);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class RateLimiterPlus {
        private long next = System.nanoTime();
        private int capacity = 0;
        private int maxCapacity;
        private long interval;

        public RateLimiterPlus(int maxCapacity, int perSecondCount) {
            this.maxCapacity = maxCapacity;
            this.interval = TimeUnit.SECONDS.toNanos(1L) / perSecondCount;
        }

        private void resync(long now) {
            if (now > next) {
                // 该期间生成的令牌数
                long count = (now - next) / interval;
                capacity = (int) Math.min(capacity + count, maxCapacity);
                next = now;
            }
        }

        private synchronized long reserve(long now) {
            resync(now);
            long at = next;
            // 令牌桶能提供的令牌数。0|1
            int provide = Math.min(1, capacity);
            // 如果不能提供令牌，则需要等待一个间隔。
            int nr = 1 - provide;
            next += nr * interval;
            capacity -= provide;
            return at;
        }

        private void acquire() {
            long now = System.nanoTime();
            long at = reserve(now);
            if (at > now) {
                try {
                    TimeUnit.NANOSECONDS.sleep(at - now);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
