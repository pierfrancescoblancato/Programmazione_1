import exceptionsCustoms.*;
import tasks.*;
import interfaceTask.ToDoListManager;

import java.util.ArrayList;
import java.util.List;

public class ToDoList implements ToDoListManager {
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
            if (task.equals(t)) throw new DuplicateTaskException("Task already exists");
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
        throw new TaskNotFoundException("No such task found");
    }

    public boolean removeTaskByTitle(String title) {
        for (Task t : tasks) {
            if (t.getTitle().equalsIgnoreCase(title)) {
                tasks.remove(t);
                return true;
            }
        }
        throw new TaskNotFoundException("No such task found");
    }

    public List<Task> getAllTasks(){
        return tasks;
    }

    public void updateTaskStatus(String title, Task.TaskStatus setTaskStatus) {
        for (Task t : tasks) {
            if (t.getTitle().equalsIgnoreCase(title)) {
                t.setTaskStatus(setTaskStatus);
                return;
            }
        }
        throw new TaskNotFoundException("No such task found");
    }

    public void toggleFavorite(String title) {
        for (Task t : tasks) {
            if (t.getTitle().equalsIgnoreCase(title)) {
                t.setFavorites(!t.isFavorites());
                return;
            }
        }
        throw new TaskNotFoundException("No such task found");
    }
}
