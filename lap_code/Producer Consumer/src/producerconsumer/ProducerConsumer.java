package producerconsumer;


public class ProducerConsumer {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();
        Producer p1 = new Producer(buffer, 1);
        Consumer c1 = new Consumer(buffer, 1);

        p1.start();
        c1.start();
    }
}