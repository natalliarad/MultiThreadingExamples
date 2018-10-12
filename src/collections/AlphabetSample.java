package collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class AlphabetSample {

    public static void main(final String[] args) {
        final BlockingQueue<Character> blockingQueue = new ArrayBlockingQueue<>(3);

        final Runnable producer = () -> {

            for (char ch = 'A'; ch <= 'Z'; ch++) {
                try {
                    blockingQueue.put(ch);
                    System.out.println(ch + " was produced");
                } catch (final InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };


        final Runnable consumer = () -> {
            char ch = '\0';
            do {
                try {
                    ch = blockingQueue.take();
                    System.out.println(ch + " was consumed");
                } catch (final InterruptedException ex) {
                    ex.printStackTrace();
                }
            } while (ch != 'Z');
        };

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
