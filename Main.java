import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager("tasks.txt");
        TaskManager manager = new TaskManager(fileManager);

        try {
            System.out.println("===== Welcome to TODO MANAGER =====");

            while (true) {
                System.out.println("Choose a mode:");
                System.out.println("1. List Mode (numbered menu)");
                System.out.println("2. Command Mode (type commands)");
                System.out.print("Your choice: ");
                String choice = scanner.nextLine().trim();

                if (choice.equals("1")) {
                    new ListMode(manager, scanner).start();
                    break;
                } else if (choice.equals("2")) {
                    new CommandMode(manager, scanner).start();
                    break;
                } else {
                    System.out.println("Invalid choice. Type 1 or 2.\n");
                }
            }

        } catch (NoSuchElementException e) {
            System.out.println("\nInput closed. Goodbye!");
        }

        scanner.close();
    }
}
