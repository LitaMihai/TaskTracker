package com.litamihai.controller;

import com.litamihai.model.TaskModel;
import com.litamihai.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class Controller {
    private final TaskService taskService;

    @Autowired
    public Controller(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskModel>> getAllTasks() {
        List<TaskModel> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> getTaskById(@PathVariable("id") String id) {
        TaskModel foundTask = taskService.getTaskById(id);
        return new ResponseEntity<>(foundTask, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TaskModel> addNewTask(@RequestBody TaskModel task) {
        TaskModel newTask = taskService.addTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TaskModel> deleteTaskById(@PathVariable("id") String id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskModel> updateTask(@RequestBody TaskModel task) {
        TaskModel updatedTask = taskService.updateTask(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
}
