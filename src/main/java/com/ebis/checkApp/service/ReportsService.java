package com.ebis.checkApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebis.checkApp.jpa.Reports;
import com.ebis.checkApp.repository.ReportsRepository;

@Service
public class ReportsService {

    @Autowired
    private ReportsRepository reportsRepository;

    public List<Reports> findAll() {
        return reportsRepository.findAll();
    }

    public Optional<Reports> findById(Integer reportId) {
        return reportsRepository.findById(reportId);
    }

    public List<Reports> findByTaskId(Integer taskId) {
        return reportsRepository.findByTaskTaskId(taskId);
    }

    public Reports save(Reports report) {
        return reportsRepository.save(report);
    }

    public void deleteById(Integer reportId) {
        reportsRepository.deleteById(reportId);
    }
}
