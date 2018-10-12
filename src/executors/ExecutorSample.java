package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class ExecutorSample {

    public static void main(final String[] args) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor(); // 1 thread that executes tasks
        final Runnable task1 = () -> {
            System.out.println("Hello from task 1. " + Thread.currentThread().getName());
        };

        final Runnable task2 = () -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello from task 2. " + Thread.currentThread().getName());
        };

        executorService.submit(task1);
        executorService.submit(task2);

        try {
            System.out.println("Trying to shut down...");
            executorService.shutdown();
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (final InterruptedException e) {
            e.printStackTrace();
            System.err.println("Task was interrupted.");
        } finally {

            if (!executorService.isTerminated()) {
                System.out.println("Start tasks cancellation...");
            }
            executorService.shutdownNow();
            System.out.println("Pool is dead!");
        }
    }
}
