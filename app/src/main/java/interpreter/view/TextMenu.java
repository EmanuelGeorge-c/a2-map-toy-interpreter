package interpreter.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;

    public TextMenu() {
        this.commands = new LinkedHashMap<>();
    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    private void printMenu() {
        System.out.println("\n=== Toy Language Interpreter Menu ===");
        for (Command command : commands.values()) {
            String line = String.format("%4s : %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine().trim();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }
            command.execute();
        }
    }
}