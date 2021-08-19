package chat.server;

import chat.Reader;
import chat.Writer;

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

            Socket socket = serverSocket.accept();
            Reader reader = new Reader(socket);
            reader.start();
            Writer writer = new Writer(socket);
            writer.start();

            reader.join(500);
            writer.join(500);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
