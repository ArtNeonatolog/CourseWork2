import java.time.LocalDate;
import java.time.LocalDateTime;

public class YearlyTask extends Task {

    public YearlyTask(String name, String description, TypeOfTask t, LocalDateTime date) {
        super(name, description, t, date);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate taskDate = this.getDate().toLocalDate();
        return taskDate.equals(localDate) || taskDate.isBefore(localDate) && taskDate.getDayOfMonth() == localDate.getDayOfMonth() && taskDate.getMonth().equals(localDate.getMonth());
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.YEARLY;
    }
}
