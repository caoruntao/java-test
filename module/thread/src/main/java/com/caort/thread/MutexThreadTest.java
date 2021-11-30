package com.caort.thread;

/**
 * @author Caort.
 * @date 2021/8/23 上午8:51
 */
public class MutexThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();
        Thread t1 = new Thread(new SimpleTest(lockA, lockB));
        Thread t2 = new Thread(new SimpleTest(lockB, lockA));
        t1.start();
        t2.start();
        t1.join();
        t1.join();
    }

    static class SimpleTest implements Runnable{
        private Object first;
        private Object second;

        public SimpleTest(Object first, Object second) {
            this.first = first;
            this.second = second;
        }


        @Override
        public void run() {
            synchronized (first){
                System.out.println("step into first lock");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                synchronized (second){

                }
            }
        }
    }
}
