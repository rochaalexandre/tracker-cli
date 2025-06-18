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

    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
            return;
        }

        switch (args[0]) {
            case ADD -> addTask(args[1]);
            case UPDATE -> updateTask(args[1], args[2]);
            case DELETE -> deleteTask(args[1]);
            case LIST -> System.out.println("Execute command " + LIST);
            case MARK_IN_PROGRESS -> System.out.println("Execute command " + MARK_IN_PROGRESS);
            case MARK_DONE -> System.out.println("Execute command " + MARK_DONE);
            default -> System.out.println("Invalid command");
        }
    }

    private static void addTask(String title) {
        System.out.println("New task title: " + title);
    }

    private static void updateTask(String id, String title) {
        System.out.println("Task ID: " + id);
        System.out.println("New task title: " + title);
    }

    private static void deleteTask(String id) {
        System.out.println("Delete task: " + id);
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
