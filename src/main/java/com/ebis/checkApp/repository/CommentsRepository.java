package com.ebis.checkApp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebis.checkApp.jpa.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    List<Comments> findByTaskTaskId(Integer taskId);
}
