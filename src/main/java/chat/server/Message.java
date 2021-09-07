package chat.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

public class Message implements Serializable {
    private static final long serialVersionUID = 3L;

    private String sender;
    private String receiver;
    private String content;
    private boolean read;

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public Message(Message message) {
        this.sender = message.getSender();
        this.receiver = message.getReceiver();
        this.content = message.getContent();
        this.read = message.isRead();
    }

    public static String getTenLastMessages(String me, String other) {
        StringBuilder sbMessages = new StringBuilder();
        List<Message> messages = new ArrayList<>();
        try {
            Chatting chatting = (Chatting) SerializationUtils.deserialize(Server.chatFileName);
            for (Message message : chatting.getMessages()) { // get all message about this correspondence
                if ((message.getSender().equals(me) && message.getReceiver().equals(other)) ||
                        (message.getSender().equals(other) && message.getReceiver().equals(me))) {
                    Message msg = new Message(message);
                    if (message.getSender().equals(me) && !message.isRead()) {
                        msg.setRead(true);
                        messages.add(msg);
                    } else {
                        messages.add(msg);
                        if (!message.isRead()) {
                            message.setRead(true);
                        }
                    }
                }
            }
            int j = 0;
            if (messages.size() > 10) {
                j = messages.size() - 10;
            }
            for (int i = j; i < messages.size(); i++) { // get ten last message
                Message message = messages.get(i);
                if (i != messages.size() - 1) {
                    sbMessages.append(message).append("\n");
                } else {
                    sbMessages.append(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return sbMessages.length() != 0 ? String.valueOf(sbMessages) : null;
    }

    @Override
    public String toString() {
        return this.read ?
                sender + ": " + content:
                "(new) " + sender + ": " + content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
