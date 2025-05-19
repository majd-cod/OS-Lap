package threaddemo;

import java.util.concurrent.Semaphore;

public class SemaphoreExample extends Thread {
    private int id;
    // Binary semaphore acting as mutex
    private static Semaphore semaphore = new Semaphore(1);

    public SemaphoreExample(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // Acquire the semaphore (enter critical section)
            semaphore.acquire();

            // Start of critical section
            System.out.println("Thread " + this.id + " entered critical section");
            // Simulate work in critical section
            Thread.sleep(500);

        } catch (InterruptedException e) {
            System.err.println("Thread " + this.id + " was interrupted");
            Thread.currentThread().interrupt();
        } finally {
            // Release the semaphore (exit critical section)
            semaphore.release();
            System.out.println("Thread " + this.id + " released the semaphore");
        }
    }

    public static void main(String[] args) {
        SemaphoreExample thread1 = new SemaphoreExample(1);
        SemaphoreExample thread2 = new SemaphoreExample(2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted");
        }

        System.out.println("All threads completed");
    }
}