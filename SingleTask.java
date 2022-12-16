import java.time.LocalDate;
import java.time.LocalDateTime;

public class SingleTask extends Task{
    public SingleTask(String name, String description, TypeOfTask t, LocalDateTime date) {
        super(name, description, t, date);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return localDate.equals(this.getDate().toLocalDate());
    }

    @Override
    public Repeatability getRepeatabilityType() {
        return Repeatability.SINGLE;
    }
}
