package com.ebis.checkApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebis.checkApp.jpa.Comments;
import com.ebis.checkApp.repository.CommentsRepository;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    public List<Comments> findAll() {
        return commentsRepository.findAll();
    }

    public Optional<Comments> findById(Integer commentId) {
        return commentsRepository.findById(commentId);
    }

    public List<Comments> findByTaskId(Integer taskId) {
        return commentsRepository.findByTaskTaskId(taskId);
    }

    public Comments save(Comments comment) {
        return commentsRepository.save(comment);
    }

    public void deleteById(Integer commentId) {
        commentsRepository.deleteById(commentId);
    }
}
