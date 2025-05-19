package osprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author shiza
 */
public class OSProcess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Verify command-line arguments
        if (args.length != 3) {
            System.out.println("No arguments provided. Using default arguments: cmd /c dir");
            // Create a new array with default arguments
            args = new String[]{"cmd", "/c", "dir"};
        }

        try {
            // Create and execute the first process
            ProcessBuilder pb = new ProcessBuilder(args[0], args[1], args[2]);
            System.out.println("Starting first process: " + args[0] + " " + args[1] + " " + args[2]);
            Process process = pb.start();

            // Read and print process output
            try (InputStream is = process.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr)) {

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Wait for the first process to complete
            int exitCode = process.waitFor();
            System.out.println("First process completed with exit code: " + exitCode);

            // Create and execute the second process
            String[] commandLine = {args[0], args[1], args[2]};
            System.out.println("Starting second process: " + args[0] + " " + args[1] + " " + args[2]);
            Process process2 = Runtime.getRuntime().exec(commandLine);
            System.out.println("Process ID: " + process2.pid());

            // Read and print the second process output
            try (InputStream is = process2.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr)) {

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Wait for the second process to complete
            int exitCode2 = process2.waitFor();
            System.out.println("Second process completed with exit code: " + exitCode2);

            // Check internet connection
            checkConnection();
        } catch (IOException e) {
            System.err.println("Error executing process: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Process interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static void checkConnection() {
        boolean isConnected;
        try {
            URL url = new URL("https://www.subu.edu.tr/");
            URLConnection conn = url.openConnection();
            conn.connect();
            isConnected = true;
        } catch (Exception e) {
            isConnected = false;
            System.out.println("Connection failed: " + e.getMessage());
        }

        if (isConnected) {
            System.out.println("Connection successful");
        }
    }
}
