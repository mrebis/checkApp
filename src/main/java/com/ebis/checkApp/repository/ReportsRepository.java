package com.ebis.checkApp.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebis.checkApp.jpa.Reports;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Integer> {
    List<Reports> findByTaskTaskId(Integer taskId);
}
