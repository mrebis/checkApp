package com.ebis.checkApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebis.checkApp.jpa.TaskPhotos;
import com.ebis.checkApp.repository.TaskPhotosRepository;

@Service
public class TaskPhotosService {

    @Autowired
    private TaskPhotosRepository taskPhotosRepository;

    public List<TaskPhotos> findAll() {
        return taskPhotosRepository.findAll();
    }

    public Optional<TaskPhotos> findById(Integer photoId) {
        return taskPhotosRepository.findById(photoId);
    }

    public List<TaskPhotos> findByTaskId(Integer taskId) {
        return taskPhotosRepository.findByTaskTaskId(taskId);
    }

    public TaskPhotos save(TaskPhotos taskPhoto) {
        return taskPhotosRepository.save(taskPhoto);
    }

    public void deleteById(Integer photoId) {
        taskPhotosRepository.deleteById(photoId);
    }
}
