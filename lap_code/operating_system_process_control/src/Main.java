import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        try{
            String line;
            Process p = Runtime.getRuntime().exec(System.getenv("windir")+"//system32//"+"tasklist.exe");
            //Works similarly to ps -e on Linux (commented out in the code).
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
