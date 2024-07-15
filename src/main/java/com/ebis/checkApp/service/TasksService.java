package com.ebis.checkApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ebis.checkApp.exception.domain.UserNotFoundException;
import com.ebis.checkApp.jpa.Tasks;
import com.ebis.checkApp.jpa.Users;
import com.ebis.checkApp.repository.TasksRepository;
import com.ebis.checkApp.repository.UsersRepository;

@Service
public class TasksService {

    @Autowired
    private TasksRepository tasksRepository;
    
    
    @Autowired
	UsersRepository userRepository;

    public List<Tasks> findAll() {
        return tasksRepository.findAll();
    }

    public Optional<Tasks> findById(Integer taskId) {
        return tasksRepository.findById(taskId);
    }

    public List<Tasks> findByAssignedTo(Integer userId) {
        return tasksRepository.findByAssignedToUserId(userId);
    }

    public List<Tasks> findByAssignedBy(Integer userId) {
        return tasksRepository.findByAssignedByUserId(userId);
    }
    
    
    public List<Tasks> getUserTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Users user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));

        return this.tasksRepository.findByAssignedToUserId(user.getUserId());
    }
    

    public Tasks save(Tasks task) {
        return tasksRepository.save(task);
    }

    public void deleteById(Integer taskId) {
        tasksRepository.deleteById(taskId);
    }
}
