package chat.server;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

public class RegistrationCommand implements Command {
    private final Chat chat;

    public RegistrationCommand(Chat chat) {
        this.chat = chat;
    }

    @Override
    public String execute() {
        return chat.registration();
    }
}
