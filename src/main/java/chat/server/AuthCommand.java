package chat.server;

public class AuthCommand implements Command {
    private final Chat chat;

    public AuthCommand(Chat chat) {
        this.chat = chat;
    }

    @Override
    public String execute() {
        return chat.auth();
    }
}
