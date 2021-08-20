package chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {
    private final Socket socket;
    private final int clientNumber;

    public Session(Socket socketForClient, int id) {
        this.socket = socketForClient;
        clientNumber = id;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            System.out.printf("Client %d connected!\n", clientNumber);
            while (true) {
                String receivedMsg = input.readUTF();
                if ("/exit".equals(receivedMsg)) {
                    socket.close();
                    System.out.printf("Client %d disconnected!\n", clientNumber);
                    break;
                } else {
                    System.out.printf("Client %d sent: %s\n", clientNumber, receivedMsg);
                    int wordCount = receivedMsg.split("\\s+").length;
                    String response = "Count is " + wordCount;
                    output.writeUTF(response);
                    System.out.printf("Sent to client %d: %s\n", clientNumber, response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
