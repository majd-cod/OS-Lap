import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.net.BindException;

public class Server {
    private static final int DEFAULT_PORT = 12345;
    private static final int MAX_PORT_ATTEMPTS = 10; // Try up to 10 ports
    private static List<PrintWriter> clients = new ArrayList<>();

    /**
     * Finds an available port starting from the specified port.
     * If the specified port is available, it will be returned.
     * Otherwise, it will try the next ports up to MAX_PORT_ATTEMPTS.
     *
     * @param startPort The port to start trying from
     * @return An available port or -1 if no available port was found
     */
    private static int findAvailablePort(int startPort) {
        for (int port = startPort; port < startPort + MAX_PORT_ATTEMPTS; port++) {
            try (ServerSocket socket = new ServerSocket()) {
                socket.setReuseAddress(true);
                socket.bind(new java.net.InetSocketAddress(port));
                return port; // Port is available
            } catch (IOException e) {
                // Port is not available, try the next one
                continue;
            }
        }
        return -1; // No available port found
    }

    public static void main(String[] args) {
        // Parse port from command line arguments if provided
        int requestedPort = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                requestedPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Geçersiz port numarası. Varsayılan port " + DEFAULT_PORT + " kullanılacak.");
            }
        }

        // Try to find an available port
        int port = findAvailablePort(requestedPort);

        if (port == -1) {
            System.err.println("Kullanılabilir port bulunamadı. Sunucu başlatılamadı.");
            System.err.println("Lütfen şunları deneyin:");
            System.err.println("1. Başka bir port kullanın (java Server <port>)");
            System.err.println("2. Çalışan diğer sunucu örneklerini kapatın");
            System.err.println("3. Sistemi yeniden başlatın");
            return;
        }

        // If we found a different port than requested, inform the user
        if (port != requestedPort) {
            System.out.println("Port " + requestedPort + " zaten kullanımda. Otomatik olarak port " + port + " kullanılıyor.");
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            System.out.println("Sunucu başlatıldı. Port: " + port + ". Bağlantı bekleniyor...");

            // Add shutdown hook to close the server socket gracefully
            final ServerSocket finalServerSocket = serverSocket;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Sunucu kapatılıyor...");
                try {
                    if (finalServerSocket != null && !finalServerSocket.isClosed()) {
                        finalServerSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Yeni bir istemci bağlandı.");

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.add(out);

                Thread clientHandler = new Thread(new ClientHandler(clientSocket));
                clientHandler.start();
            }
        } catch (IOException e) {
            if (e instanceof BindException) {
                // This should not happen as we already checked for available ports
                System.err.println("Beklenmeyen hata: Port " + port + " zaten kullanımda. Sunucu başlatılamadı.");
                System.err.println("Lütfen şunları deneyin:");
                System.err.println("1. Başka bir port kullanın (java Server <port>)");
                System.err.println("2. Çalışan diğer sunucu örneklerini kapatın");
                System.err.println("3. Sistemi yeniden başlatın");
            } else {
                System.err.println("Sunucu başlatılırken bir hata oluştu: " + e.getMessage());
            }
            e.printStackTrace();
        } finally {
            // In case the server fails to start, ensure the socket is closed
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ClientHandler implements Runnable {
        private BufferedReader in;

        ClientHandler(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Mesaj alındı: " + message);
                    broadcast(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcast(String message) {
            for (PrintWriter client : clients) {
                client.println(message);
                client.flush();
            }
        }
    }
}
