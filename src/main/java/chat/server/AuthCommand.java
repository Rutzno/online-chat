package chat.server;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

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
