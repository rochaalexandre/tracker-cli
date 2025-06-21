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
    private static int DEFAULT_TASK_ID = 1;
    TaskRepositoryImpl taskRepository;

    @BeforeEach
    void setUp() throws IOException {
        // Creates temporary file in /tmp
        tempFile = Files.createTempFile(Paths.get("/tmp"), "test-tasks", ".json");
        taskRepository = new TaskRepositoryImpl(tempFile.toString());

        // Optional: Write initial JSON content
        String initialJson = String.format("""
            [
              {"id":"%s", "description":"New task", "status":"Todo", "createdAt":"2024-08-19T17:12:24.510706", "updatedAt":"2024-08-19T17:12:24.51076"}
            ]
            """, DEFAULT_TASK_ID);
        Files.write(tempFile, initialJson.getBytes());
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
}
