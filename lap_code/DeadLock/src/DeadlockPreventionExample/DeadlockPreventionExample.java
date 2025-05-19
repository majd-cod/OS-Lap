package DeadlockPreventionExample;

public class DeadlockPreventionExample {
    // Lock objects should be final for thread safety
    public static final Object LOCK_1 = new Object();
    public static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        Thread worker1 = new WorkerThread("Worker-1", LOCK_1, LOCK_2);
        Thread worker2 = new WorkerThread("Worker-2", LOCK_1, LOCK_2);

        worker1.start();
        worker2.start();

        try {
            worker1.join();
            worker2.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted while waiting for workers");
            Thread.currentThread().interrupt();
        }

        System.out.println("All worker threads completed successfully");
    }

    /**
     * Worker thread that acquires locks in consistent order to prevent deadlocks
     */
    private static class WorkerThread extends Thread {
        private final Object firstLock;
        private final Object secondLock;

        public WorkerThread(String name, Object firstLock, Object secondLock) {
            super(name);
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            // Always acquire locks in consistent order (LOCK_1 then LOCK_2)
            synchronized (firstLock) {
                System.out.println(getName() + ": Acquired first lock");
                try {
                    // Simulate work while holding first lock
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println(getName() + " interrupted");
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println(getName() + ": Attempting to acquire second lock");
                synchronized (secondLock) {
                    System.out.println(getName() + ": Acquired both locks");
                    // Critical section work here
                }
            }
        }
    }
}