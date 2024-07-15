package com.ebis.checkApp.controller;

import java.util.List;
import java.util.UUID;

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

import com.ebis.checkApp.jpa.Comments;
import com.ebis.checkApp.service.CommentsService;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping
    public List<Comments> getAllComments() {
        return commentsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comments> getCommentById(@PathVariable int id) {
        return commentsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/task/{taskId}")
    public List<Comments> getCommentsByTaskId(@PathVariable int taskId) {
        return commentsService.findByTaskId(taskId);
    }

    @PostMapping
    public Comments createComment(@RequestBody Comments comment) {
        return commentsService.save(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comments> updateComment(@PathVariable int id, @RequestBody Comments commentDetails) {
        return commentsService.findById(id)
                .map(comment -> {
                    comment.setTask(commentDetails.getTask());
                    comment.setUser(commentDetails.getUser());
                    comment.setCommentText(commentDetails.getCommentText());
                    comment.setCreatedAt(commentDetails.getCreatedAt());
                    comment.setUpdatedAt(commentDetails.getUpdatedAt());
                    Comments updatedComment = commentsService.save(comment);
                    return ResponseEntity.ok(updatedComment);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable int id) {
        return commentsService.findById(id)
                .map(comment -> {
                    commentsService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
