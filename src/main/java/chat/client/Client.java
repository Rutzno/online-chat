package chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Client started!");
            while (true) {
                String msg = scanner.nextLine();
                if (!msg.isEmpty()) {
                    output.writeUTF(msg);
                    if ("/exit".equals(msg)) {
                        break;
                    }
                    String receivedMsg = input.readUTF();
                    System.out.println(receivedMsg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
