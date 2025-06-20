package service;

import java.util.List;
import java.util.Optional;
import model.Task;
import repository.TaskRepository;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(String title) {
        Task task = Task
            .builder()
            .description(title)
            .status("TODO").build();
        return taskRepository.addTask(task);
    }

    public boolean updateTask(String id, String title) {
        return taskRepository
            .getTask(id)
            .map(task -> Task.builder().from(task).description(title).build())
            .map(taskRepository::updateTask)
            .orElse(false);
    }

    public Optional<Task> updateTaskStatus(String id, String status) {
        return taskRepository
            .getTask(id)
            .map(task -> Task.builder().from(task).status(status).build())
            .map(taskRepository::updateTask)
            .filter(Boolean::booleanValue)
            .flatMap(updated -> taskRepository.getTask(id));
    }

    public List<Task> listTaskByStatus(String status) {
        return taskRepository.listTaskByStatus(status);
    }

    public List<Task> listTasks() {
        return taskRepository.listTask();
    }

}
