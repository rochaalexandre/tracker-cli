package repository;

import java.util.List;
import model.Task;

public interface TaskRepository {

    public Task getTask(String id);

    public Task addTask(Task task);

    public boolean updateTask(Task task);

    public List<Task> listTaskByStatus(String status);

    public List<Task> listTask();
}
