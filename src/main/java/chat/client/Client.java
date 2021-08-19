package chat.client;

import chat.Reader;
import chat.Writer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            System.out.println("Client started!");
            Writer writer = new Writer(socket);
            writer.start();
            Reader reader = new Reader(socket);
            reader.start();

            reader.join(500);
            writer.join(500);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
