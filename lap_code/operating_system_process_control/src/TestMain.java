import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestMain {
    public static void main(String[] args) {
        System.out.println("Running Main class to test tasklist execution...");
        
        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        // Run the Main class
        Main.main(args);
        
        // Restore original System.out
        System.setOut(originalOut);
        
        // Get the captured output
        String output = outputStream.toString();
        
        // Check if the output contains expected tasklist headers
        if (output.contains("Image Name") && output.contains("PID") && output.contains("Session Name")) {
            System.out.println("SUCCESS: Tasklist executed correctly!");
            System.out.println("First few lines of output:");
            
            // Print first few lines of the output
            String[] lines = output.split("\n");
            int linesToShow = Math.min(5, lines.length);
            for (int i = 0; i < linesToShow; i++) {
                System.out.println(lines[i]);
            }
        } else {
            System.out.println("FAILURE: Tasklist did not execute correctly or output format unexpected");
            System.out.println("Output received:");
            System.out.println(output);
        }
    }
}