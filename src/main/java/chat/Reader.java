package chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Reader extends Thread{
    private final Socket socket;

    public Reader(Socket socketForClient) {
        this.socket = socketForClient;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream())) {
            while (true) {
                try {
                    String receivedMsg = input.readUTF();
                    System.out.println(receivedMsg);
                } catch (IOException e) {
                    break;
                }
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
