import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileManager {

    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file, StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                int sep = line.indexOf('|');
                if (sep == -1) {
                    System.out.println("Warning: line " + lineNumber + " ignored (no '|').");
                    continue;
                }

                String status = line.substring(0, sep).trim();
                String title  = line.substring(sep + 1).trim();

                if (!status.equals("Pending") && !status.equals("Done")) {
                    System.out.println("Warning: line " + lineNumber + " ignored (unknown status).");
                    continue;
                }
                if (title.isEmpty()) {
                    System.out.println("Warning: line " + lineNumber + " ignored (empty title).");
                    continue;
                }

                tasks.add(new Task(title, status));
            }

        } catch (IOException e) {
            System.out.println("Error reading " + fileName + ": " + e.getMessage());
        }

        return tasks;
    }

    public void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName, StandardCharsets.UTF_8))) {

            for (Task task : tasks) {
                writer.write(task.getStatus() + "|" + task.getTitle());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing " + fileName + ": " + e.getMessage());
        }
    }
}
