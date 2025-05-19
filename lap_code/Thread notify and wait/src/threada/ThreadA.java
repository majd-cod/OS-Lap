package threada;


public class ThreadA {
    public static void main(String[] args) {
        ThreadB b = new ThreadB();
        b.start();

        // Wait for the calculation thread to complete
        synchronized(b) {
            try {
                b.wait();  // Wait until notified by ThreadB
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted");
            }
        }

        System.out.println("Total: " + b.total);
    }
}

class ThreadB extends Thread {
    int total;

    @Override
    public void run() {
        synchronized(this) {
            for (int i = 0; i < 100; i++) {
                total += i;
            }
            notify();  // Notify waiting thread that calculation is complete
        }
    }
}