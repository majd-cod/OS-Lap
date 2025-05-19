package threadexample1;

// Extend Thread class to implement Runnable interface
public class ThreadExample1 extends Thread {
    // Thread execution logic goes in run() method
    public void run() {
        int a = 10;
        int b = 12;
        int result = a + b;
        System.out.println("Thread started running...");
        System.out.println("Sum of two numbers: " + result);
    }

    public static void main(String args[]) {
        // Create an instance of our Thread-extended class
        ThreadExample1 t1 = new ThreadExample1();
        // Start the thread (which calls run() method)
        t1.start();
    }
}