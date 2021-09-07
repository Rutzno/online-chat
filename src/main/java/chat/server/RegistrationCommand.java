package chat.server;

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
