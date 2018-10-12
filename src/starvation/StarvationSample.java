package starvation;

public final class StarvationSample {

    private static volatile boolean isRunning = true;

    public static void main(final String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Worker(), "Reach Thread_1");
        Thread t2 = new Thread(new Worker(), "Reach Thread_2");
        Thread t3 = new Thread(new Worker(), "Reach Thread_3");
        Thread t4 = new Thread(new Worker(), "Starved Thread_4");

        t1.setPriority(10);
        t2.setPriority(8);
        t3.setPriority(5);
        t4.setPriority(1);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        Thread.sleep(1000);
        isRunning = false;
    }

    private static class Worker implements Runnable {

        private Integer counter = 0;

        @Override
        public void run() {
            while (isRunning) {
                counter++;
            }

            System.out.println(Thread.currentThread().getName() + " count before " + counter);
        }
    }
}
