package chat.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Server {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;
    public static final Map<String, Socket> users = new HashMap<>();
    public static final List<Session> sessions = new ArrayList<>();
    public static final String chatFileName = "chat.data";

    public static void main(String[] args) {
        Server server = new Server();
        server.launch();
    }

    private void launch() {
        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
//            chatFile.createNewFile();
//            serverSocket.setSoTimeout(7000);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket socket = serverSocket.accept();
                    Session session = new Session(socket);
                    session.start();
                    sessions.add(session);
                } catch (IOException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
