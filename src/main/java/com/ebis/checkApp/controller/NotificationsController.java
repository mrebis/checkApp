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

import com.ebis.checkApp.jpa.Notifications;
import com.ebis.checkApp.service.NotificationsService;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    @GetMapping
    public List<Notifications> getAllNotifications() {
        return notificationsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notifications> getNotificationById(@PathVariable int id) {
        return notificationsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Notifications> getNotificationsByUserId(@PathVariable int userId) {
        return notificationsService.findByUserId(userId);
    }

    @PostMapping
    public Notifications createNotification(@RequestBody Notifications notification) {
        return notificationsService.save(notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNotification(@PathVariable int id) {
        return notificationsService.findById(id)
                .map(notification -> {
                    notificationsService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
