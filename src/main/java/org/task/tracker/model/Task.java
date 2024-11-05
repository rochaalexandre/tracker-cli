package org.task.tracker.model;

import java.time.LocalDate;

public class Task {

    private final int id;
    private String description;
    private LocalDate createAt;
    private LocalDate updatedAt;

    public Task(int taskId, String taskDescription) {
        if (taskDescription == null || taskDescription.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }
        this.id = taskId;
        this.description = taskDescription;
        this.createAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
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
        return;
    }
}

