import tasks.*;
import interfaceTask.*;
import java.io.*;
import java.util.ArrayList;

public class TaskManagementFile implements TaskFileMenager {

    private static final String FILENAME = "07_ToDoList/src/data/task";
    private static final String SEPARATOR = "---";

    public void saveToFile(ArrayList<Task> dataToSave) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            System.out.println("Saving " + dataToSave.size() + " tasks...");
            for (Task t : dataToSave) {
                if (t instanceof UrgentTask urgentTask) {
                    writer.write("TYPE:URGENT"); writer.newLine();
                    writer.write("deadline:" + urgentTask.getDeadline()); writer.newLine();
                } else if (t instanceof RecurringTask recurringTask) {
                    writer.write("TYPE:RECURRING"); writer.newLine();
                    writer.write("frequency:" + recurringTask.getFrequency()); writer.newLine();
                } else {
                    writer.write("TYPE:TASK"); writer.newLine();
                }
                writer.write("title:" + t.getTitle()); writer.newLine();
                writer.write("description:" + t.getDescription()); writer.newLine();
                writer.write("scheduledDateTime:" + t.getScheduledDateTime()); writer.newLine();
                writer.write("priority:" + t.getPriority()); writer.newLine();
                writer.write("status:" + t.getTaskStatus()); writer.newLine();
                writer.write("favorites:" + t.isFavorites()); writer.newLine();
                writer.write(SEPARATOR); writer.newLine();
            }
            System.out.println("Saved successfully!");
        } catch (IOException e) {
            System.out.println("Error while saving: " + e.getMessage());
        }
    }

    public ArrayList<Task> loadFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();
        String type = null, title = null, description = null, frequency = null;
        long scheduledDateTime = 0, deadline = 0;
        Task.Priority priority = null;
        Task.TaskStatus status = null;
        boolean favorites = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.equals("---")) {
                    Task t = switch (type) {
                        case "URGENT"    -> new UrgentTask(title, description, scheduledDateTime,
                                priority, status, favorites, deadline);
                        case "RECURRING" -> new RecurringTask(title, description, scheduledDateTime,
                                priority, status, favorites, frequency);
                        default          -> new Task(title, description, scheduledDateTime,
                                priority, status, favorites);
                    };
                    System.out.println(t);
                    tasks.add(t);

                    type = null; title = null; description = null; frequency = null;
                    scheduledDateTime = 0; deadline = 0;
                    priority = null; status = null; favorites = false;
                    continue;
                }

                String[] parts = line.split(":", 2);
                if (parts.length != 2) continue;

                switch (parts[0]) {
                    case "TYPE"              -> type = parts[1];
                    case "title"             -> title = parts[1];
                    case "description"       -> description = parts[1];
                    case "scheduledDateTime" -> scheduledDateTime = Long.parseLong(parts[1]);
                    case "priority"          -> priority = Task.Priority.valueOf(parts[1]);
                    case "status"            -> status = Task.TaskStatus.valueOf(parts[1]);
                    case "favorites"         -> favorites = Boolean.parseBoolean(parts[1]);
                    case "deadline"          -> deadline = Long.parseLong(parts[1]);
                    case "frequency"         -> frequency = parts[1];
                }
            }
            System.out.println("-> Loaded " + tasks.size() + " tasks successfully!");
        } catch (IOException e) {
            System.out.println("Error while loading: " + e.getMessage());
        }
        return tasks;
    }
}