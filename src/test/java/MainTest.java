import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testAddCommand() {
        // Captura System.out
        ByteArrayOutputStream output = setupOutputStream();

        // Executa
        Main.main(new String[] {"add", "Buy groceries"});

        // Verifica
        assertThat(output.toString()).contains("New task title: Buy groceries");
    }

    @Test
    void testUpdateCommand() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"update", "1", "Updated description"});

        assertThat(output.toString())
            .contains("Task ID: 1")
            .contains("New task title: Updated description");
    }

    @Test
    void testDeleteCommand() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"delete", "1"});

        assertThat(output.toString()).contains("Delete task: 1");
    }

    @Test
    void testListAllTasks() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"list"});

        assertThat(output.toString()).contains("Listing all tasks");
    }

    @Test
    void testListTasksWithStatusTodo() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"list", "todo"});

        assertThat(output.toString()).contains("Listing tasks with status: todo");
    }

    @Test
    void testListTasksWithStatusInProgress() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"list", "in-progress"});

        assertThat(output.toString()).contains("Listing tasks with status: in-progress");
    }

    @Test
    void testListTasksWithStatusDone() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"list", "done"});

        assertThat(output.toString()).contains("Listing tasks with status: done");
    }

    @Test
    void testMarkTaskInProgress() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"mark-in-progress", "1"});

        assertThat(output.toString())
            .contains("Marking task 1 as in-progress");
    }

    @Test
    void testMarkTaskAsDone() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"mark-done", "1"});

        assertThat(output.toString())
            .contains("Marking task 1 as done");
    }

    @Test
    void testInvalidCommand() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {"invalid-command"});

        assertThat(output.toString()).contains("Invalid command");
    }

    @Test
    void testNoArguments() {
        ByteArrayOutputStream output = setupOutputStream();

        Main.main(new String[] {});

        assertThat(output.toString()).contains("Task Tracker CLI");
    }

    private static ByteArrayOutputStream setupOutputStream() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        return output;
    }
}
