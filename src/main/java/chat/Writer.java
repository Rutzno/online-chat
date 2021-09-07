package chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Writer extends Thread {
    private final Socket socket;

    public Writer(Socket socketForClient) {
        this.socket = socketForClient;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in);
             DataOutputStream output = new DataOutputStream(this.socket.getOutputStream())) {
            String receiver = null;
            List<String> commands = new ArrayList<>(Arrays.asList("/registration", "/auth", "/list", "/chat", "/exit"));
            while (true) {
                String msg = scanner.nextLine();
                if (!msg.isEmpty()) {
                    if ("/exit".equals(msg)) {
                        output.writeUTF(msg);
                        break;
                    } else if (msg.matches("/chat \\w+")) {
                        receiver = msg.split("\\s")[1];
                    } else if (!commands.contains(msg.split("\\s")[0])) {
                        msg = receiver + " " + msg;
                    }
                    output.writeUTF(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
