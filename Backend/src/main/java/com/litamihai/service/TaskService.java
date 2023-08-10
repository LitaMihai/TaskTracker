package com.litamihai.service;

import com.litamihai.exceptions.TaskNotFoundException;
import com.litamihai.model.TaskModel;
import com.litamihai.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<TaskModel> getAllTasks(){
        return taskRepository.findAll();
    }

    public TaskModel getTaskById(String id) {
        return taskRepository.findTaskModelById(id).orElseThrow(
                () -> new TaskNotFoundException("Task by id " + id + " was not found.")
        );
    }

    public TaskModel addTask(TaskModel task) {
        task.setId(UUID.randomUUID().toString());
        return taskRepository.save(task);
    }

    public void deleteTaskById(String id) {
        taskRepository.deleteById(id);
    }

    public TaskModel updateTask(TaskModel task) {
        return taskRepository.save(task);
    }
}
