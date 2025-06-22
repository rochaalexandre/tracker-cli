package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskRepositoryImplTest {
    private static Path tempFile;
    private static final int DEFAULT_TASK_ID = 1;
    TaskRepositoryImpl taskRepository;

    @BeforeEach
    void setUp() throws IOException {
        // Creates temporary file in /tmp
        tempFile = Files.createTempFile(Paths.get("/tmp"), "test-tasks", ".json");

        // Optional: Write initial JSON content
        String initialJson = String.format("""
            [
              {"id":"%s", "description":"New task", "status":"Todo", "createdAt":"2024-08-19T17:12:24.510706", "updatedAt":"2024-08-19T17:12:24.51076"}
            ]
            """, DEFAULT_TASK_ID);
        Files.write(tempFile, initialJson.getBytes());

        taskRepository = new TaskRepositoryImpl(tempFile.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up after each test
        Files.deleteIfExists(tempFile);
    }

    @Test
    void loadFileContent() {
        List<Task> taskList = taskRepository.loadFileContent();
        assertThat(taskList).isNotEmpty();
        assertThat(taskList.get(0).getId()).isEqualTo(DEFAULT_TASK_ID);
    }

    @Test
    void saveFileContent() throws IOException {
        //Given
        Task task = Task.builder()
            .description("New task")
            .status("Todo")
            .build();

        Task task1 = Task.builder()
            .description("New task 1")
            .status("Todo")
            .build();

        Task addedTask = taskRepository.addTask(task);
        taskRepository.addTask(task1);
        //When
        taskRepository.saveFileContent();

        //Then
        String content = Files.readString(tempFile);
        System.out.println(content);
        assertThat(content).contains(addedTask.toJson());
    }

    @Test
    void listTask_shouldReturnAllTasks() {
        // When
        List<Task> tasks = taskRepository.listTask();

        // Then
        assertThat(tasks)
            .isNotNull()
            .hasSize(1)
            .extracting(Task::getId)
            .containsExactly(1);
    }

    @Test
    void listTask_shouldReturnEmptyListWhenNoTasks() throws IOException {
        // Given
        Files.write(tempFile, "[]".getBytes());

        taskRepository = new TaskRepositoryImpl(tempFile.toString());

        // When
        List<Task> tasks = taskRepository.listTask();

        // Then
        assertThat(tasks)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void listTask_shouldReturnEmptyListWhenFileDoesNotExist() {
        // Given
        TaskRepositoryImpl repositoryWithNonExistentFile =
            new TaskRepositoryImpl("non-existent.json");

        // When
        List<Task> tasks = repositoryWithNonExistentFile.listTask();

        // Then
        assertThat(tasks)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void updateTask_shouldUpdateExistingTask() {
        // Given
        Task originalTask = taskRepository.listTask().get(0); // Task with ID 1

        Task updatedTask = Task.builder()
            .from(originalTask)
            .description("Updated Description")
            .status("in-progress")
            .build();

        // When
        boolean result = taskRepository.updateTask(updatedTask);

        // Then
        assertThat(result).isTrue();

        List<Task> allTasks = taskRepository.listTask();

        assertThat(allTasks)
            .hasSize(1) // Same number of tasks
            .extracting(Task::getId)
            .containsExactly(1); // Order may change after removal and addition

        Task taskAfterUpdate = allTasks.stream()
            .filter(t -> t.getId() == 1)
            .findFirst()
            .orElse(null);

        assertThat(taskAfterUpdate)
            .isNotNull()
            .satisfies(task -> {
                assertThat(task.getId()).isEqualTo(1);
                assertThat(task.getDescription()).isEqualTo("Updated Description");
                assertThat(task.getStatus()).isEqualTo("in-progress");
            });
    }

}
