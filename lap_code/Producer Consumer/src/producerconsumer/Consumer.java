package producerconsumer;


public class Consumer extends Thread {
    private SharedBuffer buffer;
    private int id;

    public Consumer(SharedBuffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int value = buffer.get();
            System.out.println("Consumer #" + this.id + " consumed: " + value);
        }
    }
}