package process_waitformethodexample2;

class Process_waitForMethodExample2 {

    public static void main(String[] args) {
        try {
            // Create a new process
            System.out.println("Launching MS Paint...");
            Process p1 = Runtime.getRuntime().exec("mspaint.exe");
            // Wait for the process to complete
            p1.waitFor();
            // Will continue after MS Paint is closed
            System.out.println("MS Paint has been closed.");

            System.out.println("Launching Notepad...");
            Process p2 = Runtime.getRuntime().exec("notepad.exe");
            // Wait for the process to complete
            p2.waitFor();
            // Will continue after Notepad is closed
            System.out.println("Notepad has been closed.");

        } catch (Exception ex) {
            System.out.println("Oops! An error occurred.");
        }
    }
}