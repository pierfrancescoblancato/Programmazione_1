public class Main {
    /*1. Eccezioni personalizzate
     2. File I/O
     3. IU
     */
    public static void main(String[] args) {
        TaskManager manager = new ToDoList();

        Task t1 = new Task(
                "Buy milk",
                "Get semi-skimmed milk",
                System.currentTimeMillis(),
                Task.Priority.LOW,
                Task.TaskStatus.PENDING,
                false
        );

        long oneDayInMilliseconds = 24 * 60 * 60 * 1000;
        long dueDate = System.currentTimeMillis() + oneDayInMilliseconds;

        Task t2 = new UrgentTask(
                "Study Java",
                "Review inheritance and polymorphism",
                System.currentTimeMillis(),
                Task.Priority.HIGH,
                Task.TaskStatus.IN_PROGRESS,
                false,
                dueDate
        );

        Task t3 = new RecurringTask(
                "Go to the gym",
                "Legs and shoulders workout",
                System.currentTimeMillis(),
                Task.Priority.MEDIUM,
                Task.TaskStatus.PENDING,
                false,
                "Every Monday and Thursday"
        );

        System.out.println("\n[TEST] Adding 3 tasks to the manager...");
        manager.addTask(t1);
        manager.addTask(t2);
        manager.addTask(t3);
        System.out.println("\n[TEST] entered task number: "+ Task.getTaskCount());


        System.out.println("\n[TEST] Verifying duplicate blocking (should throw an error):");
        try {
            manager.addTask(t1);
        } catch (IllegalArgumentException e) {
            System.out.println("-> OK! The system blocked the duplicate saying: " + e.getMessage());
        }

        System.out.println("\n--- CURRENT TASK LIST ---");
        for (Task t : manager.getAllTasks()) {
            System.out.println(t);
        }

        System.out.println("\n[TEST] Toggling favorites on 'Study Java'...");
        manager.toggleFavorite("Study Java");
        System.out.println("-> Is 'Study Java' in favorites now? " + manager.findTaskByTitle("study java").isFavorites());

        System.out.println("\n[TEST] Changing status of 'Buy milk' to COMPLETED...");
        manager.updateTaskStatus("Buy milk", Task.TaskStatus.COMPLETED);
        System.out.println("-> New status: " + manager.findTaskByTitle("Buy milk").getTaskStatus());

        System.out.println("\n[TEST] Removing task 'Go to the gym'...");
        System.out.println("-> Deletion successful? " + manager.removeTaskByTitle("Go to the gym"));

        System.out.println("\n--- FINAL TASK LIST AFTER TESTS ---");
        for (Task t : manager.getAllTasks()) {
            System.out.println(t);
        }
    }
}