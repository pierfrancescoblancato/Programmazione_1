import java.util.Objects;

public class Task {
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }
    public enum TaskStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    private String title;
    private String description;
    private long scheduledDateTime;
    private Priority priority;
    private TaskStatus taskStatus;
    private boolean favorites;

    public Task(String title, String description, long scheduledDateTime, Priority priority, TaskStatus taskStatus, boolean favorites) {
        setTitle(title);
        setDescription(description);
        this.scheduledDateTime = scheduledDateTime;
        this.priority = priority;
        this.taskStatus = taskStatus;
        this.favorites = favorites;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if ( title == null||  title.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: the title is null or empty");
        }
        this.title = title.trim();

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null) {
            this.description = description;}
        else {
            this.description = description.trim();
        }
    }

    public long getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(long scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", scheduledDateTime=" + getScheduledDateTime() +
                ", priority=" + getPriority() +
                ", taskStatus=" + getTaskStatus() +
                ", favorites=" + isFavorites()+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return scheduledDateTime == task.scheduledDateTime
                && Objects.equals(title, task.title)
                && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, scheduledDateTime);
    }
}
