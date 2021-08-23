package chat.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    public static final List<String> usersName = new ArrayList<>();
    public static final List<String> messages = new ArrayList<>();
    public static final List<Socket> sockets = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();
        server.launch();
    }

    private void launch() {
        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
//            serverSocket.setSoTimeout(7000);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Session(socket).start();
                } catch (IOException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
