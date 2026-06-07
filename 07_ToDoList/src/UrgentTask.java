import java.util.Objects;

public class UrgentTask extends Task{
    private long deadline;

    public UrgentTask(String title, String description, long scheduledDateTime, Priority priority, TaskStatus taskStatus, boolean favorites, long c) {
        super(title, description, scheduledDateTime, priority, taskStatus, favorites);
        this.deadline = deadline;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "UrgentTask{" +
                super.toString() +
                "deadline=" + deadline +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UrgentTask that)) return false;
        if (!super.equals(o)) return false;
        return deadline == that.deadline;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), deadline);
    }
}
