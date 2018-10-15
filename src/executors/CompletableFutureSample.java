package executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public final class CompletableFutureSample {

    public static void main(final String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> 10)
                .thenApply(i -> i+2)
                .thenApply(i -> i*2)
                .thenAccept(System.out::println)
                .thenRunAsync(() -> System.out.println("I was run from AsyncRun"));

        Thread.sleep(1000);
        System.out.println(future.get());
    }
}
