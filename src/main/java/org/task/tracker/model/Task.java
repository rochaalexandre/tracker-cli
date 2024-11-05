package org.task.tracker.model;

import java.time.LocalDate;

public class Task {

    private final int id;
    private String description;
    private LocalDate createAt;
    private LocalDate updatedAt;

    public static Task createTask(int taskId, String taskDescription, LocalDate createdAt) {
        if (taskDescription == null || taskDescription.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
        return new Task(taskId, taskDescription, createdAt);
    }

    private Task(int taskId, String taskDescription, LocalDate createdAt) {
        this.id = taskId;
        this.description = taskDescription;
        this.createAt = createdAt;
        this.updatedAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return TaskStatus.TODO;
    }

    public LocalDate getCreateAt() {

        return createAt;
    }

    public LocalDate getUpdateAt() {
        return updatedAt;
    }

    public void update() {
        this.updatedAt = LocalDate.now();
    }
}

