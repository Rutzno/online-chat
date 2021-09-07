package chat.client;

import chat.Reader;
import chat.Writer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        Client client = new Client();
        client.launch();
    }

    private void launch() {
        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream())) {
            System.out.println("Client started!");

            String receivedMsg = input.readUTF();
            System.out.println(receivedMsg);

            new Reader(socket).start();
            Writer writer = new Writer(socket);
            writer.start();
            writer.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
