package locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class LockSample {

    public Integer counter = 0;
    public static Lock lock = new ReentrantLock();

    public static void main(final String[] args) throws InterruptedException {
        LockSample resource = new LockSample();
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    lock.lock();
                    resource.counter++;
                    lock.unlock();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    lock.lock();
                    resource.counter--;
                    lock.unlock();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(resource.counter);
    }
}
