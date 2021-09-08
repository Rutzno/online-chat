package chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

public class Session extends Thread {
    private final Socket socket;
    private User user;
    private String addressee;

    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            output.writeUTF("Server: authorize or register");

            Controller controller = new Controller();

            String result;
            List<String> commands = new ArrayList<>(Arrays.asList("/registration", "/auth", "/list", "/chat", "/exit"));
            breakpoint:
            while (!Thread.currentThread().isInterrupted()) {
                String[] receivedCmd = input.readUTF().split("\\s+");
                if (!receivedCmd[0].equals("/registration") && !receivedCmd[0].equals("/auth") && user == null) {
                    output.writeUTF("Server: you are not in the chat!");
                    continue ;
                }
                if (receivedCmd[0].startsWith("/") && !commands.contains(receivedCmd[0])) {
                    output.writeUTF("Server: incorrect command!");
                    continue;
                }
                switch (receivedCmd[0]) {
                    case "/registration":
                        User tmpUser = new User(receivedCmd[1], receivedCmd[2], this.socket);
                        Chat chat = new Chat(tmpUser);
                        Command registration = new RegistrationCommand(chat);
                        controller.setCommand(registration);
                        result = controller.executeCommand();
                        if (result.equals("Server: you are registered successfully!")) {
                            this.user = new User(tmpUser);
                        }
                        output.writeUTF(result);
                        break;
                    case "/auth":
                        tmpUser = new User(receivedCmd[1], receivedCmd[2], this.socket);
                        chat = new Chat(tmpUser);
                        Command auth = new AuthCommand(chat);
                        controller.setCommand(auth);
                        result = controller.executeCommand();
                        if (result.equals("Server: you are authorized successfully!")) {
                            this.user = new User(tmpUser);
                        }
                        output.writeUTF(result);
                        break;
                    case "/list":
                        result = list(this.user);
                        output.writeUTF(result);
                        break;
                    case "/chat":
                        if (Server.users.get(receivedCmd[1]) == null) {
                            output.writeUTF("Server: the user is not online!");
                            break ;
                        }
                        this.addressee = receivedCmd[1];
                        result = Message.getTenLastMessages(this.user.getLogin(), this.addressee);
                        if (result != null) output.writeUTF(result);
                        break;
                    case "/exit":
                        Server.users.remove(user.getLogin(), this.socket);
                        this.socket.close();
                        break breakpoint;
                    default:
                        chat(receivedCmd, output);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String list(User user) {
        String result;
        if (user != null) {
            StringBuilder sb = new StringBuilder();
            for (String username : Server.users.keySet()) {
                if (!username.equals(user.getLogin())) {
                    sb.append(" ").append(username);
                }
            }
            if (sb.toString().isEmpty()) {
                result = "Server: no one online";
            } else {
                result = sb.insert(0, "Server: online:").toString();
            }
        } else {
            result = "Server: you are not in the chat!";
        }
        return result;
    }

    private void chat(String[] receivedCmd, DataOutputStream output) throws IOException, ClassNotFoundException {
        Message message, msgWithoutNew;
        String content = Arrays.toString(receivedCmd).replaceAll("[\\[\\],]", "").replaceFirst("[\\w]+\\s", "");
        if (this.addressee != null) {
            Chatting chatting = (Chatting) SerializationUtils.deserialize(Server.chatFileName);
            Session receiverSession = getReceiverSession();
            if (!this.addressee.equals(receivedCmd[0]) || receiverSession.getAddressee() == null) {
                String newAddressee = receivedCmd[0];
                message = new Message(this.user.getLogin(), newAddressee, content);
                msgWithoutNew = new Message(message);
                msgWithoutNew.setRead(true);
                chatting.getMessages().add(message);
                SerializationUtils.serialize(chatting, Server.chatFileName);
                if (!this.addressee.equals(receivedCmd[0])) return;
            } else {
                msgWithoutNew = new Message(this.user.getLogin(), this.addressee, content);
                msgWithoutNew.setRead(true);
                chatting.getMessages().add(msgWithoutNew);
                SerializationUtils.serialize(chatting, Server.chatFileName);
            }
            output.writeUTF(msgWithoutNew.toString()); // send message to me
            if (Server.users.containsKey(this.addressee) && receiverSession.getAddressee() != null
                    && receiverSession.getAddressee().equals(this.user.getLogin())) {
                DataOutputStream outputStream = new DataOutputStream(
                        Server.users.get(this.addressee).getOutputStream());
                outputStream.writeUTF(msgWithoutNew.toString()); // send to the receiver
            }
        } else {
            output.writeUTF("Server: use /list command to choose a user to text!");
        }
    }

    public String getAddressee() {
        return addressee;
    }

    public User getUser() {
        return user;
    }

    private Session getReceiverSession() {
        Session s = null;
        for (Session session : Server.sessions) {
            if (session.getUser().getLogin().equals(this.addressee)) {
                s = session;
                break ;
            }
        }
        return s;
    }
}
