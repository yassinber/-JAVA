import java.util.Scanner;

public class CommandMode {

    private TaskManager manager;
    private Scanner scanner;

    public CommandMode(TaskManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Command Mode. Type 'help' to see the commands.");

        while (true) {
            System.out.print("TODO> ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split(" ", 2);
            String command = parts[0].toLowerCase();
            String argument = (parts.length > 1) ? parts[1].trim() : "";

            switch (command) {
                case "add":       add(argument);                 break;
                case "list":      manager.displayTasks("Pending"); break;
                case "completed": manager.displayTasks("Done");    break;
                case "edit":      edit(argument);                break;
                case "delete":    delete(argument);              break;
                case "done":      changeStatus(argument, "Done");    break;
                case "pending":   changeStatus(argument, "Pending"); break;
                case "help":      showHelp();                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Unknown command: " + command + ". Type 'help'.");
            }
        }
    }

    private int parseId(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void add(String title) {
        if (manager.addTask(title)) {
            System.out.println("Task added.");
        } else {
            System.out.println("Error: title cannot be empty. Example: add Learn Java");
        }
    }

    private void edit(String argument) {
        String[] parts = argument.split(" ", 2);
        if (parts.length < 2) {
            System.out.println("Error: use  edit <ID> <new title>");
            return;
        }
        int id = parseId(parts[0]);
        if (manager.editTask(id, parts[1])) {
            System.out.println("Task updated.");
        } else {
            System.out.println("Error: invalid ID or empty title.");
        }
    }

    private void delete(String argument) {
        int id = parseId(argument);
        if (manager.deleteTask(id)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Error: invalid ID. Example: delete 1");
        }
    }

    private void changeStatus(String argument, String newStatus) {
        int id = parseId(argument);
        if (manager.changeStatus(id, newStatus)) {
            System.out.println("Task " + id + " is now " + newStatus + ".");
        } else {
            System.out.println("Error: invalid ID.");
        }
    }

    private void showHelp() {
        System.out.println("--- Commands ---");
        System.out.println("add <title>          Add a new task");
        System.out.println("list                 Show current (pending) tasks");
        System.out.println("completed            Show completed tasks");
        System.out.println("edit <ID> <title>    Change the title of a task");
        System.out.println("delete <ID>          Delete a task");
        System.out.println("done <ID>            Mark a task as done");
        System.out.println("pending <ID>         Mark a task as pending");
        System.out.println("help                 Show this guide");
        System.out.println("exit                 Quit the program");
    }
}
