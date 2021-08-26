package com.caort.thread;

/**
 * @author Reed
 * @date 2021/8/23 上午9:01
 */
public class EndlessLoopThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(new SimpleTest(lock));
        Thread t2 = new Thread(new SimpleTest(lock));
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }

    static class SimpleTest implements Runnable{
        private Object lock;

        public SimpleTest(Object lock) {
            this.lock = lock;
        }


        @Override
        public void run() {
            synchronized (lock){
                while (true){

                }
            }
        }
    }
}
