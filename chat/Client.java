import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ConnectException;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int DEFAULT_PORT = 12345;
    private static final int MAX_PORT_ATTEMPTS = 10; // Try up to 10 ports

    /**
     * Tries to connect to the server on multiple ports starting from the specified port.
     * If the connection to the specified port fails, it will try the next ports up to MAX_PORT_ATTEMPTS.
     *
     * @param startPort The port to start trying from
     * @return A connected Socket or null if no connection could be established
     */
    private static Socket tryConnectToServer(int startPort) {
        for (int port = startPort; port < startPort + MAX_PORT_ATTEMPTS; port++) {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, port);
                System.out.println("Sunucuya bağlanıldı. (Port: " + port + ")");
                return socket;
            } catch (ConnectException e) {
                // Connection refused, try the next port
                System.out.println("Port " + port + " üzerinde sunucu bulunamadı. Bir sonraki port deneniyor...");
                continue;
            } catch (IOException e) {
                // Other IO error, stop trying
                System.err.println("Bağlantı hatası: " + e.getMessage());
                break;
            }
        }
        return null; // No connection could be established
    }

    public static void main(String[] args) {
        // Parse port from command line arguments if provided
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Geçersiz port numarası. Varsayılan port " + DEFAULT_PORT + " kullanılacak.");
            }
        }

        // Try to connect to the server
        Socket socket = tryConnectToServer(port);

        if (socket == null) {
            System.err.println("Sunucuya bağlanılamadı. Lütfen sunucunun çalıştığından emin olun.");
            return;
        }

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Kullanıcı adını al
            System.out.print("Kullanıcı adınızı girin: ");
            String username = consoleInput.readLine();

            // Sunucuya kullanıcı adını gönder
            out.println(username);

            // Mesajları oku ve ekrana yazdır
            Thread messageReceiver = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.err.println("Sunucu bağlantısı kesildi: " + e.getMessage());
                }
            });
            messageReceiver.start();

            // Konsoldan girilen mesajları sunucuya gönder
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput);
            }
        } catch (IOException e) {
            System.err.println("İletişim hatası: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the socket when done
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
