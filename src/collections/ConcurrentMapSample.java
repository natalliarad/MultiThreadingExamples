package collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public final class ConcurrentMapSample {

    public static void main(final String[] args) {
        final Map<Integer, Integer> resource = new ConcurrentHashMap<>();
        final List<Thread> pool = new ArrayList<>();

        for (int i = 0; i < 10_000; i++) {
            final int val = i;
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                    resource.put(val, val);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            });
            pool.add(t);
            t.setName(String.valueOf(val));
        }

        long startTime = System.currentTimeMillis();
        pool.forEach(e -> e.start());
        pool.forEach(e -> {
            try {
                e.join();
            } catch (final InterruptedException e1) {
                e1.printStackTrace();
            }
        });

        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println("Size is " + resource.size());
    }
}
