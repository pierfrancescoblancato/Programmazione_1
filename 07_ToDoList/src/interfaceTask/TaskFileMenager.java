package interfaceTask;

import tasks.*;
import java.util.ArrayList;

public interface TaskFileMenager {

    void saveToFile(ArrayList<Task> dataToSave);

    ArrayList<Task> loadFromFile();
}
