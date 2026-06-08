package interfaceTask;

import java.util.List;
import tasks.*;

public interface ToDoListManager {

    void addTask(Task task);

    boolean removeTaskByTitle(String title);

    Task findTaskByTitle(String title);

    List<Task> getAllTasks();

    void updateTaskStatus(String title, Task.TaskStatus newStatus);

    void toggleFavorite(String title);
}