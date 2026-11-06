import java.util.Scanner;

public class ListMode {

    private TaskManager manager;
    private Scanner scanner;

    public ListMode(TaskManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addTask();          break;
                case "2": editTask();         break;
                case "3": deleteTask();       break;
                case "4": changeStatus();     break;
                case "5": listCurrent();      break;
                case "6": listCompleted();    break;
                case "7": showHelp();         break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Choose a number from 0 to 7.");
            }
        }
    }

    private void showMenu() {
        System.out.println();
        System.out.println("========== TODO MANAGER ==========");
        System.out.println("1. Add Task");
        System.out.println("2. Edit Task");
        System.out.println("3. Delete Task");
        System.out.println("4. Change Status");
        System.out.println("5. List Current Tasks");
        System.out.println("6. List Completed Tasks");
        System.out.println("7. Help");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private int readId() {
        System.out.print("Enter task ID: ");
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        if (manager.addTask(title)) {
            System.out.println("Task added.");
        } else {
            System.out.println("Error: title cannot be empty.");
        }
    }

    private void editTask() {
        manager.displayAllTasks();
        int id = readId();
        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine();
        if (manager.editTask(id, newTitle)) {
            System.out.println("Task updated.");
        } else {
            System.out.println("Error: invalid ID or empty title.");
        }
    }

    private void deleteTask() {
        manager.displayAllTasks();
        int id = readId();
        if (manager.deleteTask(id)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Error: invalid ID.");
        }
    }

    private void changeStatus() {
        manager.displayAllTasks();
        int id = readId();
        System.out.print("New status (1 = Pending, 2 = Done): ");
        String s = scanner.nextLine().trim();

        String newStatus;
        if (s.equals("1")) {
            newStatus = "Pending";
        } else if (s.equals("2")) {
            newStatus = "Done";
        } else {
            System.out.println("Error: choose 1 or 2.");
            return;
        }

        if (manager.changeStatus(id, newStatus)) {
            System.out.println("Status changed to " + newStatus + ".");
        } else {
            System.out.println("Error: invalid ID.");
        }
    }

    private void listCurrent() {
        System.out.println("--- Current Tasks ---");
        manager.displayTasks("Pending");
    }

    private void listCompleted() {
        System.out.println("--- Completed Tasks ---");
        manager.displayTasks("Done");
    }

    private void showHelp() {
        System.out.println("--- Help ---");
        System.out.println("Type the number of an option and press Enter.");
        System.out.println("Each task has an ID (its number). Use it to edit, delete or change status.");
        System.out.println("All changes are saved automatically in tasks.txt.");
    }
}
