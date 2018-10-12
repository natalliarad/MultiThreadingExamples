package synchronizers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Exchanger;

public final class ExchangerSample {

    public static void main(final String[] args) {
        final Exchanger<Message> exchanger = new Exchanger<>();
        final Thread t1 = new Job(exchanger);
        final Thread t2 = new Job(exchanger);
        t1.start();
        t2.start();
    }
}

class Message {

    public String header;
    public String body;

    public Message(final String header, final String body) {
        this.header = header;
        this.body = body;
    }
}

class Job extends Thread {

    Exchanger<Message> exchanger;
    Queue<Message> queue = new LinkedList<>();

    public Job(final Exchanger<Message> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            String header = this.getName() + " #" + i + "encode 49572049qaa32524tqt";
            String body = "akd 23542q34zofjvz ";
            queue.offer(new Message(header, body));
        }

        try {
            final Message m1 = exchanger.exchange(queue.poll());
            System.out.println(this.getName() + " exchange message: " + m1.header);

            final Message m2 = exchanger.exchange(queue.poll());
            System.out.println(this.getName() + " exchange message: " + m2.header);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}
