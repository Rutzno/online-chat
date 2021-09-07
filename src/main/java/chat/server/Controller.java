package chat.server;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

public class Controller {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public String executeCommand() {
        return this.command.execute();
    }
}
