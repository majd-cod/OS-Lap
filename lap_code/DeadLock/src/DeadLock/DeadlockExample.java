package DeadLock;


public class DeadlockExample {
    // Two lock objects for demonstration
    public static final Object LOCK_1 = new Object();
    public static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread1();
        Thread thread2 = new Thread2();

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted while waiting");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * First thread that acquires LOCK_1 then tries to get LOCK_2
     */
    private static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (LOCK_1) {
                System.out.println("Thread 1: Acquired LOCK_1");
                try {
                    // Simulate some work
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println("Thread 1 interrupted");
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Thread 1: Waiting for LOCK_2...");
                synchronized (LOCK_2) {
                    System.out.println("Thread 1: Acquired both LOCK_1 and LOCK_2");
                }
            }
        }
    }

    /**
     * Second thread that acquires LOCK_2 then tries to get LOCK_1
     */
    private static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (LOCK_2) {
                System.out.println("Thread 2: Acquired LOCK_2");
                try {
                    // Simulate some work
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println("Thread 2 interrupted");
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Thread 2: Waiting for LOCK_1...");
                synchronized (LOCK_1) {
                    System.out.println("Thread 2: Acquired both LOCK_1 and LOCK_2");
                }
            }
        }
    }
}
//o prevent the program from entering a deadlock, 
// the order of object usage (locking) in both classes must be consistent,
// such as Lock1 → Lock2. That means always locking Lock1 first, then Lock2.