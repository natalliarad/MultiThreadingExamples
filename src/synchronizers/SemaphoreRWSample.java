package synchronizers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public final class SemaphoreRWSample {

    public static void main(final String[] args) {
        Semaphore writingSemaphore = new Semaphore(1);
        Semaphore readingSemaphore = new Semaphore(10);
        List<Integer> resource = new ArrayList<>();
        List<Thread> pool = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            int val = i;
            Thread writer = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        writingSemaphore.acquire();
                        System.out.println("Hello from writing " + Thread.currentThread().getName());
                        resource.add(val);
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        writingSemaphore.release();
                    }

                }
            });
            writer.setName("Writer #" + val);
            pool.add(writer);
        }

        for (int i = 0; i < 100; i++) {
            int val = i;
            Thread reader = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                        readingSemaphore.acquire();
                        System.out.println("Hello from reading " + Thread.currentThread().getName());
                        System.out.println(resource.size());
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readingSemaphore.release();
                    }

                }
            });
            reader.setName("Reader #" + val);
            pool.add(reader);
        }

        for (Thread t : pool) {
            t.start();
        }

        pool.forEach(e -> {
            try {
                e.join();
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        System.out.println("Final result is " + resource.size());
    }
}
