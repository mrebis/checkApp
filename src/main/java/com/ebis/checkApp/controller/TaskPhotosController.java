package com.ebis.checkApp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebis.checkApp.jpa.TaskPhotos;
import com.ebis.checkApp.service.TaskPhotosService;

@RestController
@RequestMapping("/taskphotos")
public class TaskPhotosController {

    @Autowired
    private TaskPhotosService taskPhotosService;

    @GetMapping
    public List<TaskPhotos> getAllTaskPhotos() {
        return taskPhotosService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskPhotos> getTaskPhotoById(@PathVariable int id) {
        return taskPhotosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/task/{taskId}")
    public List<TaskPhotos> getTaskPhotosByTaskId(@PathVariable int taskId) {
        return taskPhotosService.findByTaskId(taskId);
    }

    @PostMapping
    public TaskPhotos createTaskPhoto(@RequestBody TaskPhotos taskPhoto) {
        return taskPhotosService.save(taskPhoto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTaskPhoto(@PathVariable int id) {
        return taskPhotosService.findById(id)
                .map(taskPhoto -> {
                    taskPhotosService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
