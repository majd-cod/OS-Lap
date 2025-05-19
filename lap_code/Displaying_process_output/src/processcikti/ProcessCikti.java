package processcikti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessCikti {
    public static void main(String[] args) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process dirProc = rt.exec("cmd /C dir");
        InputStream in = dirProc.getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}