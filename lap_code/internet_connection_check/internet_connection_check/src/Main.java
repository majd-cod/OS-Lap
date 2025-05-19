import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) {
        boolean connectivity;

        try{
            URL url = new URL("https://github.com/majd-cod");
            URLConnection con = url.openConnection();
            con.connect();
            connectivity = true;
        }catch (Exception e){
            connectivity = false;
        }if (connectivity == true){
            System.out.println("There is internet connection to the website");
        }
    }
}