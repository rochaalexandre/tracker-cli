package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Task;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private int nextId = 1;

    @Override
    public Task getTask(String id) {
        return tasks.get(Integer.parseInt(id));
    }

    @Override
    public Task addTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public boolean updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return false;
        }
        tasks.put(task.getId(), task);
        return true;
    }

    @Override
    public List<Task> listTaskByStatus(String status) {
        return tasks.values().stream()
            .filter(task -> status == null || task.getStatus().equals(status))
            .collect(Collectors.toList());
    }

    @Override
    public List<Task> listTask() {
        return new ArrayList<>(tasks.values());
    }
}
