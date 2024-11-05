package org.task.tracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

        private Task task;

        @BeforeEach
        void setUp() {
            task = Task.create(taskId, taskDescription, createdAt);
        }

        @Test
        void shouldCreateNewTaskWithId() {
            assertNotNull(task);
            assertEquals(taskId, task.getId());
        }

        @Test
        void shouldCreateTaskWithDescription() {
            assertEquals(taskDescription, task.getDescription());
        }

        @Test
        void shouldCreateTaskInTodoStatus() {
            assertEquals(TaskStatus.TODO, task.getStatus());
        }

        @Test
        void shouldCreateTaskWithCurrentDate() {
            assertEquals(taskId, task.getId());
            assertEquals(createdAt, task.getCreateAt());
        }

        @Test
        void shouldDefaultToCurrentDateIfCreateAtIsNull() {
            Task task = Task.create(taskId, taskDescription, null);
            assertEquals(createdAt, task.getCreateAt());
        }


        @Test
        void shouldUpdateTaskDescription() {
            String newDescription = "My new description";
            String description = task.updateDescription(newDescription);

            assertEquals(newDescription, description);
            assertEquals(newDescription, task.getDescription());
            assertNotEquals(taskDescription, task.getDescription());
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
            TaskStatus taskStatus = task.moveInProgress();
            assertEquals(TaskStatus.PROGRESS, task.getStatus());
            assertEquals(taskStatus, task.getStatus());
        }

        @Test
        void shouldChangeTaskStatusToDone() {
            TaskStatus taskStatus = task.markTaskAsDone();
            assertEquals(TaskStatus.DONE, task.getStatus());
            assertEquals(taskStatus, task.getStatus());
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

            assertEquals(Task.TASK_DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"", "   "})
        void shouldNotAllowUpdateTaskDescriptionWithInvalidValues(String desc) {
            Task task = Task.create(taskId, taskDescription, createdAt);
            IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                    () -> task.updateDescription(desc));

            assertEquals(Task.TASK_DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());
        }
    }
}
