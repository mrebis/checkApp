package com.ebis.checkApp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebis.checkApp.jpa.Tasks;
import com.ebis.checkApp.service.TasksService;

@RestController
@RequestMapping("/task")
public class TasksController {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TasksService tasksService;

    @GetMapping
    public List<Tasks> getAllTasks() {
        return tasksService.findAll();
    }
    
    @GetMapping("/usertask")
    public List<Tasks> getUserTasks() {
        logger.debug("Getting User Tasks");
        return this.tasksService.getUserTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable int id) {
        return tasksService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tasks createTask(@RequestBody Tasks task) {
        return tasksService.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tasks> updateTask(@PathVariable int id, @RequestBody Tasks taskDetails) {
        return tasksService.findById(id)
                .map(task -> {
                    task.setAssignedBy(taskDetails.getAssignedBy());
                    task.setAssignedTo(taskDetails.getAssignedTo());
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setDueDate(taskDetails.getDueDate());
                    task.setLocation(taskDetails.getLocation());
                    task.setStatus(taskDetails.getStatus());
                    task.setCreatedAt(taskDetails.getCreatedAt());
                    task.setUpdatedAt(taskDetails.getUpdatedAt());
                    Tasks updatedTask = tasksService.save(task);
                    return ResponseEntity.ok(updatedTask);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable int id) {
        return tasksService.findById(id)
                .map(task -> {
                    tasksService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
