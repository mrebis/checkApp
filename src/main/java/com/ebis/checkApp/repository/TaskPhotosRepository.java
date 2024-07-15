package com.ebis.checkApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebis.checkApp.jpa.TaskPhotos;

@Repository
public interface TaskPhotosRepository extends JpaRepository<TaskPhotos, Integer> {
    List<TaskPhotos> findByTaskTaskId(Integer taskId);
}

