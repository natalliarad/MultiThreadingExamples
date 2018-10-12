package locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class TryLockSample {

    public static void main(final String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();

            try {
                Thread.sleep(1000);
                System.out.println("I'm new Thread");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
        Thread.sleep(100);
        lock.lock();
        System.out.println("I'm main Thread");
        lock.unlock();
    }
}
