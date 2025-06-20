package service;

import java.util.List;
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
        Task savedTask = taskRepository.getTask(id);
        if (savedTask == null) {
            return false;
        }
        Task updatedTask = Task.builder().from(savedTask).description(title).build();
        return taskRepository.updateTask(updatedTask);
    }

    public List<Task> listTaskByStatus(String status) {
        return taskRepository.listTaskByStatus(status);
    }

    public List<Task> listTasks() {
        return taskRepository.listTask();
    }

}
