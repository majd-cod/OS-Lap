package process_waitformethodexample3;

public class Process_waitForMethodExample3 {
    public static void main(String[] args) {
        try {
            System.out.println("Launching MS Paint and Notepad together.\n");
            System.out.println("1. Starting MS Paint...");
            Process p1 = Runtime.getRuntime().exec("mspaint.exe");
            System.out.println("2. Starting Notepad...\n\n");
            Process p2 = Runtime.getRuntime().exec("notepad.exe");

            // Wait for p1 process to complete
            p1.waitFor();
            // This will execute after MS Paint is closed
            System.out.println("MS Paint has been closed.");

            // Wait for p2 process to complete
            p2.waitFor();
            System.out.println("Notepad has been closed.");
        } catch (Exception ex) {
            System.out.println("Oops! An error occurred: " + ex);
        }
    }
}