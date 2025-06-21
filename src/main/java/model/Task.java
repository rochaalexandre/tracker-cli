package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // DateTimeFormatter for serializing/deserializing dates
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    public Task(Integer id, String description, String status, LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", status='" + status + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String description;
        private String status;
        private LocalDateTime createdAt = LocalDateTime.now();
        private LocalDateTime updatedAt = LocalDateTime.now();

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder from(Task task) {
            this.id = task.getId();
            this.description = task.getDescription();
            this.status = task.getStatus();
            this.createdAt = task.getCreatedAt();
            this.updatedAt = task.getUpdatedAt();
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Task build() {
            return new Task(id, description, status, createdAt, updatedAt);
        }
    }

    public String toJson() {
        return "{\"id\":\"" + id + "\", \"description\":\"" + description.strip() +
               "\", \"status\":\"" + status +
               "\", \"createdAt\":\"" + createdAt.format(formatter) + "\", \"updatedAt\":\"" +
               updatedAt.format(formatter) + "\"}";
    }

    public static Task fromJson(String json) {
        String sanitizedContent = json.replaceAll("[{}]", "").replace("\"", "");
        System.out.println(" JSON content: " + sanitizedContent);
        String[] parts = sanitizedContent.split(",");

        int parsedId = Integer.parseInt(parts[0].split(":")[1].strip());

        return Task.builder()
            .id(parsedId)
            .description(parts[1].split(":")[1].strip())
            .status(parts[2].split(":")[1].strip())
            .createdAt(LocalDateTime.parse(parts[3].split("[a-z]:")[1].strip(), formatter))
            .updatedAt(LocalDateTime.parse(parts[4].split("[a-z]:")[1].strip(), formatter))
            .build();
    }
}
