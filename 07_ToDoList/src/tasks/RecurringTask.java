package tasks;
import java.util.Objects;

public class RecurringTask extends Task {

    private String frequency;

    public RecurringTask(String title, String description, long scheduledDateTime, Priority priority, TaskStatus taskStatus, boolean favorites, String frequency) {
        super(title, description, scheduledDateTime, priority, taskStatus, favorites);
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "RecurringTask{" +
                super.toString() +
                "frequency='" + frequency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RecurringTask that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(frequency, that.frequency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), frequency);
    }
}
