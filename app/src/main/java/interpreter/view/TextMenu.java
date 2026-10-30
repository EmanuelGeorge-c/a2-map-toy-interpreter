package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("choose an option ");
            String option = scanner.nextLine().trim();

            if (option.equals("0")) {
                System.out.println("bye!");
                break;
            }

            IStmt selectedProgram = switch (option) {
                case "1" -> Examples.EX1;
                case "2" -> Examples.EX2;
                case "3" -> Examples.EX3;
                case "4" -> Examples.EX4;
                default -> null;
            };

            if (selectedProgram == null) {
                System.out.println("invalid option!\n");
                continue;
            }

            try {
                PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedProgram);
                Controller controller = new Controller(new Repository(state), true);

                System.out.println("\n--- Program execution ---");
                controller.allStep();
                System.out.println("--- Execution completed ---");
                System.out.println("Out: " + state.getOut() + "\n");
            } catch (MyException e) {
                System.out.println("Execution Error: " + e.getMessage() + "\n");
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("=== Toy Language Interpreter ===");
        System.out.println("1 - Ex1: int v; v=2; print(v)");
        System.out.println("2 - Ex2: int a; int b; a=2+3*5; b=a+1; print(b)");
        System.out.println("3 - Ex3: bool a; int v; a=true; (if a then v=2 else v=3); print(v)");
        System.out.println("4 - Ex4: bool a; a=2; print(a)");
        System.out.println("0 - Exit");
    }
}