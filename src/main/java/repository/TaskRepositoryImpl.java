package repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.Task;

public class TaskRepositoryImpl implements TaskRepository {
    private final Path FILE_PATH;
    private final List<Task> tasks;
    private int nextId;


    public TaskRepositoryImpl(String fileName) {
        FILE_PATH = Paths.get(fileName == null || fileName.isEmpty() ? "tasks.json" : fileName);
        this.tasks = loadFileContent();
        this.nextId = loadMaxIdFromFile() + 1;
    }

    public List<Task> loadFileContent() {
        try {
            List<String> lines = Files.readAllLines(FILE_PATH);
            return lines.stream()
                .filter(Predicate.not(line -> List.of("[", "]").contains(line)))
                .map(Task::fromJson)
                .collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }

    }

    private int loadMaxIdFromFile() {
        return tasks.stream()
            .mapToInt(Task::getId)
            .max()
            .orElse(0);  // Start from 1 if no tasks exist
    }

    @Override
    public Optional<Task> getTask(String id) {
        return Optional.empty();
    }

    @Override
    public Task addTask(Task task) {
        Task savedTask = Task.builder().from(task).id(nextId++).build();
        tasks.add(savedTask);
        return savedTask;
    }

    @Override
    public boolean updateTask(Task task) {
        return false;
    }

    @Override
    public List<Task> listTaskByStatus(String status) {
        return List.of();
    }

    @Override
    public List<Task> listTask() {
        return List.of();
    }

    public void saveFileContent() {
        String jsonTasksContent = tasks.stream().map(Task::toJson)
            .collect(Collectors.joining("," + System.lineSeparator()));

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(System.lineSeparator());
        sb.append(jsonTasksContent);
        sb.append(System.lineSeparator());
        sb.append("]");
        sb.append(System.lineSeparator());

        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardCharsets.UTF_8)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error saving file: " + FILE_PATH, e);
        }
    }
}
