package TestThread;

public class TestThread {

    /**
     * Main method
     */
    public static void main(String[] args) {
        // Create threads
        PrintChar printA = new PrintChar('a', 100);
        PrintChar printB = new PrintChar('b', 100);
        PrintNum print100 = new PrintNum(100);

        // Start threads
        print100.start();
        printA.start();
        printB.start();
    }
}

/**
 * Thread for printing a character multiple times
 */
class PrintChar extends Thread {

    private char character;
    private int count;

    /**
     * Constructor for character printing thread
     * @param c Character to print
     * @param t Number of times to print
     */
    public PrintChar(char c, int t) {
        character = c;
        count = t;
    }

    /**
     * Run method prints the character specified number of times
     */
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.print(character);
        }
    }
}

/**
 * Thread for printing numbers from 1 to n
 */
class PrintNum extends Thread {

    private int upperLimit;

    /**
     * Constructor for number printing thread
     * @param n Upper limit of numbers to print
     */
    public PrintNum(int n) {
        upperLimit = n;
    }

    /**
     * Run method prints numbers from 1 to upperLimit
     */
    public void run() {
        for (int i = 1; i <= upperLimit; i++) {
            System.out.print(" " + i);
        }
    }
}