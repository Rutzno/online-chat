package chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Writer extends Thread {
    private final Socket socket;

    public Writer(Socket socketForClient) {
        this.socket = socketForClient;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in);
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                String msg = scanner.nextLine();
                if (!msg.isEmpty()) {
                    output.writeUTF(msg);
                    if ("/exit".equals(msg)) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
