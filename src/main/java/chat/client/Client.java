package chat.client;

import chat.Reader;
import chat.Writer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;
    private static String name;

    public static void main(String[] args) {
        Client client = new Client();
        client.launch();
    }

    private void launch() {
        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Client started!");

            String receivedMsg = input.readUTF();
            System.out.println(receivedMsg);

            while (name == null) {
                String msg = scanner.nextLine();
                output.writeUTF(msg);
                String msgFromServer = "Server: this name is already taken! Choose another one.";
                receivedMsg = input.readUTF();
                if (receivedMsg.equals(msgFromServer)) {
                    System.out.println(receivedMsg);
                    continue;
                } else if (!receivedMsg.isEmpty()) {
                    System.out.println(receivedMsg);
                }
                name = msg;
            }
            new Reader(socket).start();
            Writer writer = new Writer(socket);
            writer.start();
            writer.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
