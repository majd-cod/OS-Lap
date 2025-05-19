package threadaB;


class ThreadaB {
    public static void main(String[] args) {
        // Create and start the calculation thread
        ThreadB calculationThread = new ThreadB();
        calculationThread.start();

        // Synchronize on the calculation thread object
        synchronized (calculationThread) {
            try {
                System.out.println("Waiting for calculation thread to complete...");
                calculationThread.wait();  // Main thread waits here
            } catch (InterruptedException e) {
                System.err.println("Main thread was interrupted while waiting");
                e.printStackTrace();
            }

            // This executes after being notified
            System.out.println("Total sum: " + calculationThread.total);
        }
    }
}

/**
 * Thread that calculates the sum of numbers from 0 to 99
 */
class ThreadB extends Thread {
    int total;  // Stores the calculation result

    @Override
    public void run() {
        // Synchronize on this thread object
        synchronized (this) {
            // Calculate sum of 0 to 99
            for (int i = 0; i < 100; i++) {
                total += i;
            }

            // Notify any waiting threads that calculation is complete
            notify();
            System.out.println("Calculation completed. Notifying waiting threads...");
        }
    }
}