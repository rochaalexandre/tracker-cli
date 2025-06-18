package service;

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
        Task updatedTask = Task.builder().from(savedTask).description(title).build();
        return taskRepository.updateTask(updatedTask);
    }


}
