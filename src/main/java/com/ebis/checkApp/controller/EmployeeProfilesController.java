package com.ebis.checkApp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebis.checkApp.jpa.EmployeeProfiles;
import com.ebis.checkApp.service.EmployeeProfilesService;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/profile")
public class EmployeeProfilesController {

    @Autowired
    private EmployeeProfilesService employeeProfilesService;

    @GetMapping
    public List<EmployeeProfiles> getAllProfiles() {
        return employeeProfilesService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<EmployeeProfiles> getProfileById(@PathVariable Integer userId) {
        return employeeProfilesService.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public EmployeeProfiles createProfile(@RequestBody EmployeeProfiles profile) {
        return employeeProfilesService.createprofile(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeProfiles> updateProfile(@PathVariable int id, @RequestBody EmployeeProfiles profileDetails) {
        return employeeProfilesService.findById(id)
                .map(profile -> {
                    profile.setUser(profileDetails.getUser());
                    profile.setPosition(profileDetails.getPosition());
                    profile.setPhoneNumber(profileDetails.getPhoneNumber());
                    profile.setAddress(profileDetails.getAddress());
                    profile.setCreatedAt(profileDetails.getCreatedAt());
                    profile.setUpdatedAt(profileDetails.getUpdatedAt());
                    EmployeeProfiles updatedProfile = employeeProfilesService.save(profile);
                    return ResponseEntity.ok(updatedProfile);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProfile(@PathVariable int id) {
        return employeeProfilesService.findById(id)
                .map(profile -> {
                    employeeProfilesService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
