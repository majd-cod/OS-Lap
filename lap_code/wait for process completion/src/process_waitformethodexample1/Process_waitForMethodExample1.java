package process_waitformethodexample1;


public class Process_waitForMethodExample1 {
    public static void main(String[] args) {
        try {
            // Create a new process
            System.out.println("Launching MS Paint program...");
            Process p = Runtime.getRuntime().exec("mspaint.exe");

            // Wait for the process to terminate
            p.waitFor();

            // This will execute after you manually close MS Paint
            System.out.println("MS Paint has been closed.");
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex);
        }
    }
}