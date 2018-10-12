package readWriteLocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ScalableForReadingThreadSafeArray<E> {

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    private final List<E> list = new ArrayList<>(1_000);

    public void add(final E object) {
        writeLock.lock();
        try {
            list.add(object);
            System.out.println("Element  " + object + " was added by Thread "
                    + Thread.currentThread().getName());
        } finally {
            writeLock.unlock();
        }
    }

    public E get(final int i) {
        readLock.lock();
        try {
            System.out.println("Element" + list.get(i) +" was extracted by " + Thread.currentThread().getName());
            return list.get(i);
        } finally {
            readLock.unlock();
        }
    }
}
