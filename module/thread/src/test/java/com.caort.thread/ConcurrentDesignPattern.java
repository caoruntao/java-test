package com.caort.thread;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author reed
 * @date 2022/7/25 08:47
 */
public class ConcurrentDesignPattern {

    @Test
    public void balking(){
        // 多线程的if判断，不等待条件成立。
        // 如双重检查锁
    }

    @Test
    public void guardedSuspension() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        GuardedObject<String> guardedObject = new GuardedObject<>();
        // 模拟条件满足。需要自己找对应的GuardedObject
        scheduledExecutorService.schedule(() -> guardedObject.onChange("msg"), 2, TimeUnit.SECONDS);

        String msg = guardedObject.get(StringUtils::hasText);
        System.out.println("接受到消息:" + msg);
    }

    @Test
    public void guardedSuspensionPlus(){
    }

    /**
     * 多线程的if判断，可以等待条件成立。
     *
     * @param <T>
     */
    public static class GuardedObject<T> {
        private T obj;
        private ReentrantLock lock = new ReentrantLock();
        private Condition done = lock.newCondition();

        public T get(Predicate<T> predicate) {
            lock.lock();
            try {
                // await唤醒后，条件不一定成立，因此需要while在此判断一下
                while (!predicate.test(obj)) {
                    try {
                        boolean await = done.await(10, TimeUnit.SECONDS);
                        if (!await) {
                            // 超时处理
                            break;
                        }
                    } catch (InterruptedException e) {
                        // 被打断处理
                        break;
                    }
                }
                return obj;
            } finally {
                lock.unlock();
            }
        }

        public void onChange(T obj) {
            lock.lock();
            try {
                this.obj = obj;
                done.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 不用自己找映射关系
     */
    public static class GuardedObjectPlus {
        private static final Map<String, GuardedObjectPlus> cache = new ConcurrentHashMap<>();
        private volatile Object obj;
        private ReentrantLock lock = new ReentrantLock();
        private Condition done = lock.newCondition();

        public static GuardedObjectPlus create(String k) {
            GuardedObjectPlus guardedObjectPlus = new GuardedObjectPlus();
            cache.put(k, guardedObjectPlus);
            return guardedObjectPlus;
        }

        public static void fireEvent(String k, Object obj){
            GuardedObjectPlus guardedObjectPlus = cache.get(k);
            guardedObjectPlus.onChange(obj);
        }

        public Object get(Predicate<Object> predicate) {
            lock.lock();
            try {
                // await唤醒后，条件不一定成立，因此需要while在此判断一下
                while (!predicate.test(obj)) {
                    try {
                        boolean await = done.await(10, TimeUnit.SECONDS);
                        if (!await) {
                            // 超时处理
                            break;
                        }
                    } catch (InterruptedException e) {
                        // 被打断处理
                        break;
                    }
                }
                return obj;
            } finally {
                lock.unlock();
            }
        }

        public void onChange(Object obj) {
            lock.lock();
            try {
                this.obj = obj;
            } finally {
                lock.unlock();
            }
        }
    }
}
