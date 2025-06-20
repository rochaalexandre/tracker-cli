package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Task;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private int nextId = 1;

    @Override
    public Optional<Task> getTask(String id) {
        if (tasks.containsKey(Integer.parseInt(id))) {
            return Optional.of(tasks.get(Integer.parseInt(id)));
        }
        return Optional.empty();
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
