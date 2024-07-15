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

import com.ebis.checkApp.jpa.Reports;
import com.ebis.checkApp.service.ReportsService;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping
    public List<Reports> getAllReports() {
        return reportsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reports> getReportById(@PathVariable int id) {
        return reportsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/task/{taskId}")
    public List<Reports> getReportsByTaskId(@PathVariable int taskId) {
        return reportsService.findByTaskId(taskId);
    }

    @PostMapping
    public Reports createReport(@RequestBody Reports report) {
        return reportsService.save(report);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReport(@PathVariable int id) {
        return reportsService.findById(id)
                .map(report -> {
                    reportsService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
