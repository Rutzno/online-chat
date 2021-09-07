package chat;

import java.util.Scanner;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

public class Servr {
    private final Scanner scanner = new Scanner(System.in);
    private final String[] actions = new String[] {"sent", "joined", "left", "connected", "disconnected"};

    public void run() {
        String input;
        Message output;
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();

            if (input.contains(actions[0])) {
                String[] person = input.split(actions[0], 2);
                String sender = person[0];
                String content = person[1];
                output = new Message(sender, content);
                System.out.println(output);
            }
        }
    }
}
