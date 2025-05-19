package producerconsumer;


public class Producer extends Thread {
    private SharedBuffer buffer;
    private int id;

    public Producer(SharedBuffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            buffer.put(i);
            System.out.println("Producer #" + this.id + " produced: " + i);
            try {
                sleep((int)(Math.random() * 100)); // Simulate processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}