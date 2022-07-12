package com.caort.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author reed
 * @date 2022/6/29 15:29
 */
public class ForkJoinPoolTest {
    @Test
    public void forkJoin() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        AccumulateTask task = new AccumulateTask(0, 10000);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(task);
        try {
            Integer result = submit.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }finally {
            forkJoinPool.shutdown();
        }
    }

    public static class AccumulateTask extends RecursiveTask<Integer> {

        private int start;
        private int end;

        public AccumulateTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 1000) {
                System.out.println(Thread.currentThread().getId() + "计算" + start + "-" + end);
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += i;
                }
                return sum;
            }

            AccumulateTask fork1 = new AccumulateTask(start, (start + end) >> 1);
            AccumulateTask fork2 = new AccumulateTask((start + end) >> 1, end);
            invokeAll(fork1, fork2);
//            fork1.fork();
//            fork2.fork();
            return fork2.join() + fork1.join();
        }
    }
}
