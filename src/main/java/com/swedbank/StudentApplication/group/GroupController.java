package com.swedbank.StudentApplication.group;

import com.swedbank.StudentApplication.task.Task;
import com.swedbank.StudentApplication.task.TaskService;
import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/groups")
public class GroupController {

    private static final Logger log = LoggerFactory.getLogger(GroupController.class);

    private final GroupService groupService;
    private final TaskService taskService;

    public GroupController(GroupService groupService, TaskService taskService) {
        this.groupService = groupService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.findAll();
        return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Group> getGroupByid(@PathVariable final long id) {
        Group group = groupService.findById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody final Group group) {
        Group savedGroup = groupService.save(group);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Group> updateGroup(@RequestBody final Group group) {
        Group updateGroup = groupService.update(group);
        return new ResponseEntity<>(updateGroup, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable final long id) {
        groupService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllGroups() {
        groupService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<Set<Task>> getGroupTasks(@PathVariable long id) throws TaskNotFoundException {
        Group group = groupService.findById(id);
        return new ResponseEntity<>(group.getTasks(), HttpStatus.OK);
    }

    @PatchMapping("/{gid}/tasks/{id}")
    public ResponseEntity<?> addTaskToGroup(@PathVariable long gid, @PathVariable long id) {
        Group group = groupService.findById(gid);
        Task task = taskService.findById(id);

        Set<Task> groupTasks = group.getTasks();
        groupTasks.add(task);
        group.setTasks(groupTasks);
        groupService.save(group);

        task.setGroup(group);
        taskService.save(task);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{gid}/tasks/{id}")
    public ResponseEntity<?> deleteTaskFromGroup(@PathVariable long gid, @PathVariable long id) {
        Group group = groupService.findById(gid);
        Task task = taskService.findById(id);
        Set<Task> groupTasks = group.getTasks();
        if (groupTasks.contains(task)) {
            groupTasks.remove(task);
            group.setTasks(groupTasks);

            groupService.saveAndFlush(group);

            task.setGroup(null);
            taskService.save(task);
        }
        return ResponseEntity.ok().build();
    }
}
