package com.ebis.checkApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebis.checkApp.jpa.Subscriptions;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Integer> {
    Subscriptions findByUserUserId(Integer userId);
}
