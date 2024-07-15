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

import com.ebis.checkApp.jpa.Subscriptions;
import com.ebis.checkApp.service.SubscriptionsService;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionsController {

    @Autowired
    private SubscriptionsService subscriptionsService;

    @GetMapping
    public List<Subscriptions> getAllSubscriptions() {
        return subscriptionsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscriptions> getSubscriptionById(@PathVariable int id) {
        return subscriptionsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Subscriptions> getSubscriptionByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(subscriptionsService.findByUserId(userId));
    }

    @PostMapping
    public Subscriptions createSubscription(@RequestBody Subscriptions subscription) {
        return subscriptionsService.save(subscription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscriptions> updateSubscription(@PathVariable int id, @RequestBody Subscriptions subscriptionDetails) {
        return subscriptionsService.findById(id)
                .map(subscription -> {
                    subscription.setUser(subscriptionDetails.getUser());
                    subscription.setPlan(subscriptionDetails.getPlan());
                    subscription.setStartDate(subscriptionDetails.getStartDate());
                    subscription.setEndDate(subscriptionDetails.getEndDate());
                    subscription.setStatus(subscriptionDetails.getStatus());
                    subscription.setCreatedAt(subscriptionDetails.getCreatedAt());
                    subscription.setUpdatedAt(subscriptionDetails.getUpdatedAt());
                    Subscriptions updatedSubscription = subscriptionsService.save(subscription);
                    return ResponseEntity.ok(updatedSubscription);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubscription(@PathVariable int id) {
        return subscriptionsService.findById(id)
                .map(subscription -> {
                    subscriptionsService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
