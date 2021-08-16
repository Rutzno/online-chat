package chat;

public class Message {
    private String sender;
    private String content;

    public Message(String sender, String content) {
        this.sender = sender.trim();
        this.content = content.trim();
    }

    @Override
    public String toString() {
        return sender + ": " + content;
    }
}
