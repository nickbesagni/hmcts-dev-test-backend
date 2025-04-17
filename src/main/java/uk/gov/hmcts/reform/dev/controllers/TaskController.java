package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository repositories;

    @GetMapping(produces = "application/json")
    public List<Task> getAllTasks() {
        return repositories.findAll();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return repositories.findById(id).orElseThrow(() -> new TaskNotFoundException("No task with that ID exists"));
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return repositories.save(task);
    }

    @PutMapping("/{id}")
    public Optional<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return Optional.ofNullable(repositories.findById(id).map(existingTask -> {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setStatus(task.getStatus());
            existingTask.setDueDateTime(task.getDueDateTime());
            return repositories.save(existingTask);
        }).orElseThrow(() -> new TaskNotFoundException("Task cannot be found")));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        repositories.deleteById(id);
    }
}
