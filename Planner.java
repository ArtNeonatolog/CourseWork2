import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Planner {
    private final Map<Integer, Task> tasks = new HashMap<>();

    public void addTask (Task task) {
        this.tasks.put(task.getId(), task);
    }

    public void removeTask (int id) throws TasksNotFoundException {
        if (this.tasks.containsKey(id)) {
            this.tasks.remove(id);
        } else {
            throw new TasksNotFoundException ();
        }

    }

    public Collection<Task> getAllTasks () {
        return this.tasks.values();
    }

    public Collection<Task> getTasksForDay (LocalDate localDate) {
        List<Task> tasksForDay = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.appearsIn(localDate)) {
                tasksForDay.add(task);
            }
            }
        return tasksForDay;
        }

    public void printMenu() {
        System.out.println(
                """
                        1. Добавить задачу
                        2. Удалить задачу
                        3. Получить задачу на указанный день
                        0. Выход
                        """
        );
    }
}
