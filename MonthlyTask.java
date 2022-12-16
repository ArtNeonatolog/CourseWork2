import java.time.LocalDate;
import java.time.LocalDateTime;

public class MonthlyTask extends Task{
    public MonthlyTask(String name, String description, TypeOfTask t, LocalDateTime date) {
        super(name, description, t, date);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getDate().toLocalDate();
        return taskDate.equals(localDate) || (taskDate.isBefore(localDate) && taskDate.getDayOfMonth () == localDate.getDayOfMonth());
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.MONTHLY;
    }
}
