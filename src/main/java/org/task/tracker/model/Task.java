package org.task.tracker.model;

import java.time.LocalDate;

public class Task {

    private final int id;
    private final LocalDate createAt;
    private String description;
    private LocalDate updatedAt;
    private TaskStatus taskStatus;

    public static Task create(int taskId, String taskDescription, LocalDate createdAt) {
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
        this.taskStatus = TaskStatus.TODO;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public TaskStatus getStatus() {
        return this.taskStatus;
    }

    public LocalDate getCreateAt() {
        return this.createAt;
    }

    public LocalDate getUpdateAt() {
        return this.updatedAt;
    }

    public TaskStatus moveInProgress() {
        this.taskStatus = TaskStatus.PROGRESS;
        this.updatedAt = LocalDate.now();
        return TaskStatus.PROGRESS;
    }

    public TaskStatus markTaskAsDone() {
        this.taskStatus = TaskStatus.DONE;
        this.updatedAt = LocalDate.now();
        return TaskStatus.DONE;
    }
}

