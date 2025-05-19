package testthread;


public class TestThread {

    public final static int NUM_THREADS = 3;
    public static int sharedCounter = 0;
    public static int sharedCounter2 = 0;

    // Lock object for synchronization
    static class LockObject extends Object {}
    static public LockObject lock = new LockObject();

    /**
     * Custom thread class that operates on shared data
     */
    static class SyncThread extends Thread {

        @Override
        public void run() {
            System.out.println("Thread " + getName() + ": Entered");

            synchronized (lock) {
                /**
                 * ******** Critical Section Start **********
                 */
                System.out.println("Thread " + getName() +
                        ": Critical section started, lock acquired");

                // Modify shared data
                ++sharedCounter;
                --sharedCounter2;

                System.out.println("Thread " + getName() +
                        ": Critical section ended, releasing lock");
                /**
                 * ******** Critical Section End **********
                 */
            }
        }
    }

    /**
     * Main method
     */
    public static void main(String[] args) throws InterruptedException {
        SyncThread[] threads = new SyncThread[NUM_THREADS];
        System.out.println("Starting test process");

        System.out.println("Synchronizing access to shared data");
        synchronized (lock) {

            System.out.println("Creating threads");
            for (int i = 0; i < NUM_THREADS; ++i) {
                threads[i] = new SyncThread();
                threads[i].start();
            }

            System.out.println("Waiting for shared data operation");
            try {
                Thread.sleep(3000); // Hold lock for 3 seconds
            } catch (InterruptedException e) {
                System.out.println("Wait interrupted");
            }

            System.out.println("Releasing locked resource");
        }

        System.out.println("Waiting for threads to complete");
        for (int i = 0; i < NUM_THREADS; ++i) {
            threads[i].join();
        }

        System.out.println("Final values - sharedCounter: " + sharedCounter +
                ", sharedCounter2: " + sharedCounter2);
    }
}