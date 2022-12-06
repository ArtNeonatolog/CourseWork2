import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Main {

    private static final Planner PLANNER = new Planner();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH.mm");

    private static void printTasksForDay (Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        Collection<Task> tasksForDay = PLANNER.getTasksForDay(localDate);
        System.out.println("Задачи на " + localDate + ": " + tasksForDay);
    }

    private static LocalDate readDate(Scanner scanner){
        while (true) {
            try {
                System.out.println("Введите дату задачи в формате d.MM.yyyy");
                String dateLine = scanner.nextLine();
                return LocalDate.parse(dateLine, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Введен неверный формат даты!");
            }
        }
    }

    private static LocalTime readTime(Scanner scanner){
        while (true) {
            try {
                System.out.println("Введите время задачи в формате hh.mm");
                String dateLine = scanner.nextLine();
                return LocalTime.parse(dateLine, TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Введен неверный формат времени!");
            }
        }
    }



    private static void addTask (Scanner scanner) {
        String name = readString ("Введите название задачи:", scanner);
        String description = readString ("Введите описание задачи", scanner);
        LocalDateTime taskDate = readDateTime (scanner);
        TypeOfTask typeOfTask = readType(scanner);
        Repeatability repeatability = readRepeatability(scanner);
        Task task = switch (repeatability) {
            case SINGLE -> new SingleTask(name, description, typeOfTask, taskDate);
            case DAILY -> new DailyTask(name, description, typeOfTask, taskDate);
            case WEEKLY -> new WeeklyTask(name, description, typeOfTask, taskDate);
            case MONTHLY -> new MonthlyTask(name, description, typeOfTask, taskDate);
            case YEARLY -> new YearlyTask(name, description, typeOfTask, taskDate);
        };
        PLANNER.addTask(task);

    }

    private static Repeatability readRepeatability(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Выберите тип повторяемости задачи: ");
                for (Repeatability repeatability : Repeatability.values()) {
                    System.out.println(repeatability.ordinal() + ". ");
                }
                System.out.println("Выберите тип повторяемости задачи: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return Repeatability.values() [ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен неверный тип повторяемости задачи!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип повторяемости задачи не найден");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner scanner) {
       LocalDate localDate = readDate(scanner);
       LocalTime localTime = readTime(scanner);
       return localDate.atTime(localTime);
    }

    private static String readString (String message, Scanner scanner) {
        while (true) {
            System.out.println(message);
            String readString = scanner.nextLine();
            if (readString == null || readString.isBlank()) {
                System.out.println("Введено пустое значение");
            } else {
                return readString;
            }
        }
    }
    private static TypeOfTask readType (Scanner scanner) {
        while (true) {
            try {
                System.out.println("Выберите тип задачи: ");
                for (TypeOfTask typeOfTask : TypeOfTask.values()) {
                    System.out.println(typeOfTask.ordinal() + ". ");
                }
                System.out.println("Выберите тип задачи: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
               return TypeOfTask.values() [ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен неправильный номер задачи!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найден");
            }
        }
    }
private static void removeTasks (Scanner scanner) {
    System.out.println("Вcе задачи: ");
    for (Task task : PLANNER.getAllTasks()) {
        System.out.println(task.getId() + " " + task.getName() + " " + task.getDate() + " " + task.getRepeatabilityType());
    }
        while (true) {
            try {
                System.out.println("Выберите задачу для удаления: ");
                String idLine = scanner.nextLine();
                int id = Integer.parseInt(idLine);
                PLANNER.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введен неправильный id задачи!");
            } catch (TasksNotFoundException e) {
                System.out.println("Задача для удаления не найдена");
            }
        }

}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Planner planner = new Planner();
        Task task1 = new DailyTask("Задача 1", "Надо поспать после курсовой", TypeOfTask.PERSONAL, LocalDateTime.now());
        Task task2 = new DailyTask("Задача 2", "Надо поесть после курсовой", TypeOfTask.PERSONAL, LocalDateTime.now());
        Task task3 = new DailyTask("Задача 3", "Надо погулять после курсовой", TypeOfTask.PERSONAL, LocalDateTime.now());
        Task task4 = new DailyTask("Задача 4", "Надо сходить в магазин после курсовой", TypeOfTask.PERSONAL, LocalDateTime.now());
        Task task5 = new DailyTask("Задача 5", "Надо отдохнкть после курсовой", TypeOfTask.PERSONAL, LocalDateTime.now());
        planner.addTask(task1);
        planner.addTask(task2);
        planner.addTask(task3);
        planner.addTask(task4);
        planner.addTask(task5);
        
        try (scanner) {
            label:
            while (true) {
                planner.printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            addTask(scanner);
                            break;
                        case 2:
                            removeTasks(scanner);
                            break;
                        case 3:
                            printTasksForDay(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }
}
