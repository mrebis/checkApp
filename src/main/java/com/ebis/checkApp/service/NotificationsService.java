package com.ebis.checkApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebis.checkApp.jpa.Notifications;
import com.ebis.checkApp.repository.NotificationsRepository;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    public List<Notifications> findAll() {
        return notificationsRepository.findAll();
    }

    public Optional<Notifications> findById(Integer notificationId) {
        return notificationsRepository.findById(notificationId);
    }

    public List<Notifications> findByUserId(Integer userId) {
        return notificationsRepository.findByUserUserId(userId);
    }

    public Notifications save(Notifications notification) {
        return notificationsRepository.save(notification);
    }

    public void deleteById(Integer notificationId) {
        notificationsRepository.deleteById(notificationId);
    }
}

