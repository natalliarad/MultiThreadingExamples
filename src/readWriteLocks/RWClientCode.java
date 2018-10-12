package readWriteLocks;

import java.util.concurrent.ThreadLocalRandom;

public final class RWClientCode {

    public static void main(final String[] args) {
        ScalableForReadingThreadSafeArray<Integer> list = new ScalableForReadingThreadSafeArray<>();
        list.add(0);

        for (int i = 0; i <= 1000; i++) {
            new Thread(() -> {
                list.get(0);

                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

            final int val = i;

            new Thread(() -> {
                list.add(val);

                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }
}
