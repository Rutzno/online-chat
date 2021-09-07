package chat.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chat {
    private User user;

    public Chat(User user) {
        this.user = user;
    }

    public String registration() {
        String result = null;
        try {
            Chatting chatting = (Chatting) SerializationUtils.deserialize(Server.chatFileName);
            List<User> users = chatting != null ? chatting.getUsers() : null;
            if (users != null) {
                for (User user : users) {
                    if (user.getLogin().equals(this.user.getLogin())) {
                        result = "Server: this login is already taken! Choose another one.";
                        break;
                    }
                }
            }
            if (this.user.getPassword().length() < 8) {
                result = "Server: the password is too short!";
            }
            if (result != null) return result;
            String pwd = String.valueOf(this.user.getPassword().hashCode());
            this.user.setPassword(pwd);
            if (chatting == null) {
                chatting = new Chatting(new ArrayList<>(Collections.singletonList(this.user)));
            } else {
                chatting.getUsers().add(this.user);
            }
            SerializationUtils.serialize(chatting, Server.chatFileName);
            result = "Server: you are registered successfully!";
            Server.users.put(this.user.getLogin(), this.user.getSocket());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String auth() {
        String result = null;
        boolean found = false;
        String pwd = String.valueOf(this.user.getPassword().hashCode());
        try {
            Chatting chatting = (Chatting) SerializationUtils.deserialize(Server.chatFileName);
            List<User> users = chatting != null ? chatting.getUsers() : null;
            if (users != null) {
                for (User user : users) {
                    if (user.getLogin().equals(this.user.getLogin())) {
                        found = true;
                        if (user.getPassword().equals(pwd)) {
                            result = "Server: you are authorized successfully!";
                            Server.users.put(this.user.getLogin(), this.user.getSocket());
                        } else {
                            result = "Server: incorrect password!";
                        }
                        break;
                    }
                }
            }
            if (!found) {
                result = "Server: incorrect login!";
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
