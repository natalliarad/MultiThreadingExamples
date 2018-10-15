package executors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public final class StampedLockService {

    public static void main(final String[] args) {
        final ExecutorService service = Executors.newFixedThreadPool(2);
        final Map<String, Integer> map = new HashMap<>();
        final StampedLock lock = new StampedLock();

        service.submit(() -> {
            final long stamp = lock.writeLock();

            try {
                TimeUnit.SECONDS.sleep(1);
                map.put("Moscow", 11000000);
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlockWrite(stamp);
            }
        });

        final Runnable readTask = () -> {
            long stamp;

            if ((stamp = lock.tryOptimisticRead()) != 0L) {
                final Integer population = map.get("Moscow");
                if (lock.validate(stamp)) {
                    System.out.println(population + "from optimistic reading");
                }
            }

            stamp = lock.readLock();

            try {
                System.out.println(map.get("Moscow") + " from Read Lock");
                TimeUnit.SECONDS.sleep(1);
            } catch (final InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlockRead(stamp);
            }
        };

        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
    }
}
