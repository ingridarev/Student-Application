package com.swedbank.StudentApplication.task;

import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/tasks")
public class TaskController {

    private TaskService service;

    @Autowired
    public TaskController(TaskService taskService) {
        this.service = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return service.findAll();
    }

    @GetMapping("{id}")
    ResponseEntity<Task> getById(@PathVariable long id) {
        Task task = service.findById(id);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @PatchMapping()
    public void updateTask(@RequestBody Task task) {
        service.update(task);
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task newTask = service.save(task);
        return ok(newTask);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) throws TaskNotFoundException {
        service.delete(id);
        return ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ok().build();
    }

}
