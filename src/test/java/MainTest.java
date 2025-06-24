import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testAddCommand() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"add", "Buy groceries"});

        //Then
        assertThat(output.toString()).contains("New task description: Buy groceries");
    }

    @Test
    void testUpdateCommand() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"update", "1", "Updated description"});

        //Then
        assertThat(output.toString())
            .contains("New task description: Updated description");
    }

    @Test
    void testDeleteCommand() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"delete", "1"});

        //Then
        assertThat(output.toString()).contains("Delete task: 1");
    }

    @Test
    void testListAllTasks() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"list"});

        //Then
        assertThat(output.toString()).contains("Listing all tasks");
    }

    @Test
    void testListTasksWithStatusTodo() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"list", "todo"});

        //Then
        assertThat(output.toString()).contains("Listing tasks with status: todo");
    }

    @Test
    void testListTasksWithStatusInProgress() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"list", "in-progress"});

        //Then
        assertThat(output.toString()).contains("Listing tasks with status: in-progress");
    }

    @Test
    void testListTasksWithStatusDone() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"list", "done"});

        //Then
        assertThat(output.toString()).contains("Listing tasks with status: done");
    }

    @Test
    void testMarkTaskInProgress() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"mark-in-progress", "1"});

        //Then
        assertThat(output.toString()).contains("Marking task 1 as in-progress");
    }

    @Test
    void testMarkTaskAsDone() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"mark-done", "1"});

        //Then
        assertThat(output.toString()).contains("Marking task 1 as done");
    }

    @Test
    void testInvalidCommand() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {"invalid-command"});

        //Then
        assertThat(output.toString()).contains("Invalid command");
    }

    @Test
    void testNoArguments() {

        //Given
        ByteArrayOutputStream output = setupOutputStream();

        //When
        Main.main(new String[] {});

        //Then
        assertThat(output.toString()).contains("Task Tracker CLI");
    }

    private static ByteArrayOutputStream setupOutputStream() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        return output;
    }
}
