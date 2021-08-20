package chat.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 23456;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");
            int clientNumber = 0;
//            serverSocket.setSoTimeout(15000);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Session(socket, ++clientNumber).start();
                } catch (IOException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
