import tasks.*;
import java.util.ArrayList;

public class FileSaveThread implements Runnable {
    private final TaskManagementFile fileManager;
    private final ArrayList<Task> tasks;

    public FileSaveThread(TaskManagementFile fileManager, ArrayList<Task> tasks) {
        this.fileManager = fileManager;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        System.out.println("\n[THREAD] Saving in background on: "
                + Thread.currentThread().getName());
        fileManager.saveToFile(tasks);
        System.out.println("[THREAD] Background save completed!");
    }
}