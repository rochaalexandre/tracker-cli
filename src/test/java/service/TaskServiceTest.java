package service;

import static org.assertj.core.api.Assertions.assertThat;

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

    // Additional test methods can be added here for other TaskService functionality
}
