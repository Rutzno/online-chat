package chat;

import java.util.Scanner;

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
