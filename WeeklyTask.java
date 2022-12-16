import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task {
    public WeeklyTask(String name, String description, TypeOfTask t, LocalDateTime date) {
        super(name, description, t, date);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getDate().toLocalDate();
        return taskDate.equals(localDate) || (taskDate.isBefore(localDate) && taskDate.getDayOfWeek().equals(localDate.getDayOfWeek()));
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.WEEKLY;
    }
}
