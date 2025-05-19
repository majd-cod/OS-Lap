package producerconsumer;

/**
 * Shared buffer between producer and consumer
 * Implements proper synchronization using wait/notify
 */
public class SharedBuffer {
    private int data;
    private boolean available = false;

    /**
     * Gets data from buffer (Consumer)
     * @return the consumed data
     */
    public synchronized int get() {
        while (!available) {
            try {
                wait(); // Wait until data is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        available = false;
        notifyAll();
        return data;
    }

    /**
     * Puts data into buffer (Producer)
     * @param value the data to produce
     */
    public synchronized void put(int value) {
        while (available) {
            try {
                wait(); // Wait until buffer is empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data = value;
        available = true;
        notifyAll();
    }
}