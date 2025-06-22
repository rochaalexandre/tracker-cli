import repository.TaskRepositoryImpl;
import service.TaskService;

public class Main {
    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String MARK_IN_PROGRESS = "mark-in-progress";
    public static final String MARK_DONE = "mark-done";
    public static final String LIST = "list";

    public static final String STATUS_TODO = "todo";
    public static final String STATUS_IN_PROGRESS = "in-progress";
    public static final String STATUS_DONE = "done";

    private static final TaskService taskService = new TaskService(new TaskRepositoryImpl());

    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
            return;
        }

        switch (args[0]) {
            case ADD -> addTask(args[1]);
            case UPDATE -> updateTask(args[1], args[2]);
            case DELETE -> deleteTask(args[1]);
            case LIST -> listTasks(args.length >= 2 ? args[1] : null);
            case MARK_IN_PROGRESS -> markTaskInProgress(args[1]);
            case MARK_DONE -> markTaskAsDone(args[1]);
            default -> System.out.println("Invalid command");
        }
    }

    private static void addTask(String description) {
        taskService.addTask(description);
        System.out.println("New task description: " + description);
    }

    private static void updateTask(String id, String description) {
        taskService.updateTask(id, description);
        System.out.println("Task ID: " + id);
        System.out.println("New task description: " + description);
    }

    private static void deleteTask(String id) {
        System.out.println("Delete task: " + id);
    }

    private static void listTasks(String status) {
        if (status == null || status.isEmpty()) {
            System.out.println("Listing all tasks");
            taskService.listTasks().forEach(System.out::println);
        } else {
            System.out.println("Listing tasks with status: " + status);
            taskService.listTasks().forEach(System.out::println);
        }
    }

    private static void markTaskAsDone(String id) {
        taskService.updateTaskStatus(id, STATUS_DONE);
        System.out.println("Marking task " + id + " as " + STATUS_DONE);
    }

    private static void markTaskInProgress(String id) {
        taskService.updateTaskStatus(id, STATUS_IN_PROGRESS);
        System.out.println("Marking task " + id + " as " + STATUS_IN_PROGRESS);
    }

    private static void printUsage() {
        System.out.println("Task Tracker CLI");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  task-cli " + ADD + " \"description\"");
        System.out.println("  task-cli " + UPDATE + " <id> \"new description\"");
        System.out.println("  task-cli " + DELETE + " <id>");
        System.out.println("  task-cli " + MARK_IN_PROGRESS + " <id>");
        System.out.println("  task-cli " + MARK_DONE + " <id>");
        System.out.println("  task-cli " + LIST);
        System.out.println(
            "  task-cli " + LIST + " [" + STATUS_TODO + "|" + STATUS_IN_PROGRESS + "|" +
            STATUS_DONE + "]");
    }
}
