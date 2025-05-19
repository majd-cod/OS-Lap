package threadexample2;

class NewThread implements Runnable {
    String name;
    Thread thread;

    NewThread(String name) {
        this.name = name;
        thread = new Thread(this, name);
        System.out.println("New thread created: " + thread + "\n");
        thread.start();
    }

    public void run() {
        try {
            // Countdown from 5 to 1
            for (int j = 5; j > 0; j--) {
                System.out.println(name + ": " + j);
                Thread.sleep(1000); // Pause for 1 second
            }
        } catch (InterruptedException e) {
            System.out.println(name + " thread interrupted");
        }
        System.out.println(name + " thread exiting.");
    }
}

public class ThreadExample2 {
    public static void main(String args[]) {
        // Create three new threads
        new NewThread("Thread 1");
        new NewThread("Thread 2");
        new NewThread("Thread 3");

        try {
            // Main thread waits for 8 seconds
            Thread.sleep(8000);
        } catch (InterruptedException exception) {
            System.out.println("Main Thread interrupted");
        }
        System.out.println("Main Thread exiting");
    }
}