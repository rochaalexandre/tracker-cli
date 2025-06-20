package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        Task updatedTask = repository.getTask(String.valueOf(task.getId())).get();
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

    @Test
    void shouldUpdateTaskStatusWhenTaskExists() {
        // Given
        Task originalTask = taskService.addTask("Test Task");
        int taskId = originalTask.getId();
        String newStatus = "IN_PROGRESS";

        // When
        Optional<Task> result = taskService.updateTaskStatus(String.valueOf(taskId), newStatus);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getStatus()).isEqualTo(newStatus);
        assertThat(result.get().getDescription()).isEqualTo("Test Task");
        assertThat(result.get().getId()).isEqualTo(taskId);
    }

    @Test
    void shouldReturnEmptyWhenTaskNotFound() {
        // Given
        String nonExistentId = "999";
        String newStatus = "DONE";

        // When
        Optional<Task> result = taskService.updateTaskStatus(nonExistentId, newStatus);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenRepositoryUpdateFails() {
        // Given
        // When
        Optional<Task> result = taskService.updateTaskStatus("2", "INVALID_STATUS");

        // Then
        assertThat(result).isNotPresent();
    }

    @Test
    void shouldPreserveOtherFieldsWhenUpdatingStatus() {
        // Given
        Task originalTask = taskService.addTask("Important Task");
        String taskId = String.valueOf(originalTask.getId());
        String originalDescription = originalTask.getDescription();

        // When
        Optional<Task> result = taskService.updateTaskStatus(taskId, "DONE");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getDescription()).isEqualTo(originalDescription);
        assertThat(result.get().getId()).isEqualTo(Integer.parseInt(taskId));
        assertThat(result.get().getStatus()).isEqualTo("DONE");
    }

    @Test
    void shouldReturnUpdatedTaskFromRepository() {
        // Given
        String status = "COMPLETED";
        Task originalTask = taskService.addTask("Test Task");
        Integer taskId = originalTask.getId();

        // When
        Optional<Task> result = taskService.updateTaskStatus(String.valueOf(taskId), status);

        // Then
        assertThat(result).isPresent();

        // Verify it's actually the updated task from the repository
        List<Task> allTasks = taskService.listTasks();
        Optional<Task> taskInRepository = allTasks.stream()
            .filter(task -> task.getId() == taskId)
            .findFirst();

        assertThat(taskInRepository).isPresent();
        assertThat(taskInRepository.get().getStatus()).isEqualTo(status);
        assertThat(result.get()).isEqualTo(taskInRepository.get());
    }

    private Task createTaskWithId(String description, String status) {
        return Task.builder()
            .description(description)
            .status(status)
            .build();
    }
}
