package com.ebis.checkApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebis.checkApp.jpa.Subscriptions;
import com.ebis.checkApp.repository.SubscriptionsRepository;

@Service
public class SubscriptionsService {

    @Autowired
    private SubscriptionsRepository subscriptionsRepository;

    public List<Subscriptions> findAll() {
        return subscriptionsRepository.findAll();
    }

    public Optional<Subscriptions> findById(Integer subscriptionId) {
        return subscriptionsRepository.findById(subscriptionId);
    }

    public Subscriptions findByUserId(Integer userId) {
        return subscriptionsRepository.findByUserUserId(userId);
    }

    public Subscriptions save(Subscriptions subscription) {
        return subscriptionsRepository.save(subscription);
    }

    public void deleteById(Integer subscriptionId) {
        subscriptionsRepository.deleteById(subscriptionId);
    }
}
