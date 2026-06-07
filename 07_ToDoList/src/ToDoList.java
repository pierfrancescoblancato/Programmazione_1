import java.util.ArrayList;
import java.util.List;

public class ToDoList implements TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();

    public ToDoList() {
        this.tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        for (Task t : tasks) {
            if (task.equals(t)) throw new IllegalArgumentException("Task already exists");
        }
        tasks.add(task);
        System.out.println("-> Tasks added successfully!");

    }

    public Task findTaskByTitle(String title) {
        for (Task t : tasks) {
            if (t.getTitle().equalsIgnoreCase(title)) {
                return t;
            }
        }
        return null;
    }

    public boolean removeTaskByTitle(String title) {
        for (Task t : tasks) {
            if (t.getTitle().equalsIgnoreCase(title)) {
                tasks.remove(t);
                return true;
            }
        }
        return false;
    }

    public List<Task> getAllTasks(){
        return tasks;
    }

    public void updateTaskStatus(String title, Task.TaskStatus setTaskStatus) {
        for (Task t : tasks) {
            if (t.getTitle().equalsIgnoreCase(title)) {
                t.setTaskStatus(setTaskStatus);
                break;
            }
        }
    }

    public void toggleFavorite(String title) {
        for (Task t : tasks) {
            if (t.getTitle().equalsIgnoreCase(title)) {
                t.setFavorites(!t.isFavorites());
                break;
            }
        }
    }
}
