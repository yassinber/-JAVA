import java.util.ArrayList;

public class TaskManager {

    private ArrayList<Task> tasks;
    private FileManager fileManager;

    public TaskManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.tasks = fileManager.loadTasks();
    }

    private boolean isValidId(int id) {
        return id >= 1 && id <= tasks.size();
    }

    public boolean addTask(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        tasks.add(new Task(title.trim(), "Pending"));
        fileManager.saveTasks(tasks);
        return true;
    }

    public boolean editTask(int id, String newTitle) {
        if (!isValidId(id) || newTitle == null || newTitle.trim().isEmpty()) {
            return false;
        }
        tasks.get(id - 1).setTitle(newTitle.trim());
        fileManager.saveTasks(tasks);
        return true;
    }

    public boolean deleteTask(int id) {
        if (!isValidId(id)) {
            return false;
        }
        tasks.remove(id - 1);
        fileManager.saveTasks(tasks);
        return true;
    }

    public boolean changeStatus(int id, String newStatus) {
        if (!isValidId(id)) {
            return false;
        }
        if (!newStatus.equals("Pending") && !newStatus.equals("Done")) {
            return false;
        }
        tasks.get(id - 1).setStatus(newStatus);
        fileManager.saveTasks(tasks);
        return true;
    }

    public void displayTasks(String status) {
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getStatus().equals(status)) {
                System.out.println((i + 1) + ". " + t.getTitle());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks.");
        }
    }

    public void displayAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.println((i + 1) + ". [" + t.getStatus() + "] " + t.getTitle());
        }
    }
}
