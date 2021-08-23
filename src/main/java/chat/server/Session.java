package chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Session extends Thread {
    private final Socket socket;
    private String name;

    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            output.writeUTF("Server: write your name");
            while (name == null) {
                String receivedName = input.readUTF();
                if (Server.usersName.contains(receivedName)) {
                    output.writeUTF("Server: this name is already taken! Choose another one.");
                } else {
                    name = receivedName;
                    Server.usersName.add(name);
                    String messages = getTenLastMessages();
                    output.writeUTF(String.valueOf(messages));
                    Server.sockets.add(socket);
                }
            }
            while (true) {
                String receivedMsg = input.readUTF();
                if ("/exit".equals(receivedMsg)) {
                    Server.sockets.remove(socket);
                    socket.close();
                    break;
                } else {
                    String response = name + ": " + receivedMsg;
                    for (Socket socket : Server.sockets) { // broadcast
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                        outputStream.writeUTF(response);
                    }
                    Server.messages.add(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTenLastMessages() {
        StringBuilder sbMessages = new StringBuilder();
        List<String> messages = Server.messages;
        int j = 0;
        if (messages.size() > 10) {
            j = messages.size() - 10;
        }
        for (int i = j; i < messages.size(); i++) {
            String message = messages.get(i);
            if (i != messages.size() - 1) {
                sbMessages.append(message).append("\n");
            } else {
                sbMessages.append(message);
            }
        }
        return String.valueOf(sbMessages);
    }
}
