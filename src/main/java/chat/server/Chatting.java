package chat.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

public class Chatting implements Serializable {
    private static final long serialVersionUID = 5L;

    private List<User> users;
    private List<Message> messages;

    public Chatting(List<User> users) {
        this.users = users;
        this.messages = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
