package org.task.tracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class TaskTest {
    private final int taskId = 1;
    private final String taskDescription = "task description";
    private final LocalDate createdAt = LocalDate.now();

    @Nested
    class WhenUsingValidValues {

        @Test
        void shouldCreateNewTaskWithId() {
            Task task = Task.create(taskId, taskDescription, createdAt);
            assertNotNull(task);
            assertEquals(taskId, task.getId());
        }

        @Test
        void shouldCreateTaskWithDescription() {
            Task task = Task.create(taskId, taskDescription, createdAt);
            assertEquals(taskDescription, task.getDescription());
        }

        @Test
        void shouldCreateTaskInTodoStatus() {
            Task task = Task.create(taskId, taskDescription, createdAt);
            assertEquals(TaskStatus.TODO, task.getStatus());
        }

        @Test
        void shouldCreateTaskWithCurrentDate() {
            Task task = Task.create(taskId, taskDescription, createdAt);
            assertEquals(taskId, task.getId());
            assertEquals(createdAt, task.getCreateAt());
        }

        @Test
        void shouldDefaultToCurrentDateIfCreateAtIsNull() {
            Task task = Task.create(taskId, taskDescription, null);
            assertEquals(createdAt, task.getCreateAt());
        }

    }

    @Nested
    class WhenUpdatingTask {
        Task task;
        LocalDate createdAt = LocalDate.of(2024, 11, 1);

        @BeforeEach
        void setUp() {
            task = Task.create(taskId, taskDescription, createdAt);
        }

        @Test
        void shouldUpdateTheUpdateAtProperty() {
            task.moveInProgress();
            assertEquals(LocalDate.now(), task.getUpdateAt());
        }

        @Test
        void shouldChangeTaskStatusToInProgress() {
            task.moveInProgress();
            assertEquals(TaskStatus.PROGRESS, task.getStatus());
        }
    }

    @Nested
    class WhenUsingInvalidValues {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"", "   "})
        void shouldNotAllowTaskWithoutDescription(String desc) {
            IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                    () -> Task.create(0, desc, LocalDate.now()));

            assertEquals("Task description cannot be empty", exception.getMessage());
        }
    }
}
