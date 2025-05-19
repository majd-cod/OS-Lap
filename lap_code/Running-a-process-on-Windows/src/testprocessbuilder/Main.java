package testprocessbuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
public class Main {
    public static void main(String[] args) {
        try{
            String threeCommands[]=
                    {
                            "cmd","/c","dir","&&"
                            ,"ping","google.com","&&"
                            ,"tasklist"
                    };
            ProcessBuilder builder = new ProcessBuilder(threeCommands);
            builder.directory(new File("C://"));
            builder.redirectErrorStream();

            Process subProcess = builder.start();

            // this reads from the subprocess's output stream
            BufferedReader subProcessInputReader =
                    new BufferedReader(new InputStreamReader(subProcess.getInputStream()));
            String line = null;
            while ((line = subProcessInputReader.readLine()) != null)
                System.out.println(line);
            subProcessInputReader.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}