package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryTaskRepository;
import repository.TaskRepository;

class TaskServiceTest {
    private TaskRepository repository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
        taskService = new TaskService(repository);
    }

    @Test
    void shouldAddNewTask() {
        // given
        String taskDescription = "Buy groceries";

        // when
        Task addedTask = taskService.addTask(taskDescription);

        // then
        assertThat(addedTask).isNotNull();
        assertThat(addedTask.getId()).isPositive();
        assertThat(addedTask.getDescription()).isEqualTo(taskDescription);
        assertThat(addedTask.getStatus()).isEqualTo("TODO");
        assertThat(addedTask.getCreatedAt()).isNotNull();
        assertThat(addedTask.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldUpdateExistingTask() {
        // given
        Task task = taskService.addTask("Original task");
        String newDescription = "Updated task";

        // when
        boolean updated = taskService.updateTask(String.valueOf(task.getId()), newDescription);

        // then
        assertThat(updated).isTrue();
        Task updatedTask = repository.getTask(String.valueOf(task.getId()));
        assertThat(updatedTask.getDescription()).isEqualTo(newDescription);
        assertThat(updatedTask.getStatus()).isEqualTo(task.getStatus());
        assertThat(updatedTask.getId()).isEqualTo(task.getId());
    }

    @Test
    void shouldReturnFalseWhenUpdatingNonExistentTask() {
        // when
        boolean updated = taskService.updateTask("999", "New description");

        // then
        assertThat(updated).isFalse();
    }

    @Test
    void shouldReturnAllTasksWhenListingTasks() {
        // Given
        List<Task> expectedTasks = Arrays.asList(
            createTaskWithId("Task 1", "TODO"),
            createTaskWithId("Task 2", "DONE")
        );
        expectedTasks.forEach(repository::addTask);

        // When
        List<Task> result = taskService.listTasks();

        // Then
        assertThat(result).isEqualTo(expectedTasks);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksExist() {
        // When
        List<Task> result = taskService.listTasks();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnTasksWithSpecificStatusWhenListingByStatus() {
        // Given
        String status = "TODO";
        List<Task> expectedTasks = Arrays.asList(
            createTaskWithId("Task 1", "TODO"),
            createTaskWithId("Task 3", "TODO")
        );
        expectedTasks.forEach(repository::addTask);

        // When
        List<Task> result = taskService.listTaskByStatus(status);

        // Then
        assertThat(result).isEqualTo(expectedTasks);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksMatchStatus() {
        // Given
        String status = "IN_PROGRESS";
        List<Task> expectedTasks = Arrays.asList(
            createTaskWithId("Task 1", "TODO"),
            createTaskWithId("Task 3", "TODO")
        );
        expectedTasks.forEach(repository::addTask);

        // When
        List<Task> result = taskService.listTaskByStatus(status);

        // Then
        assertThat(result).isEmpty();
    }

    private Task createTaskWithId(String description, String status) {
        return Task.builder()
            .description(description)
            .status(status)
            .build();
    }
}
