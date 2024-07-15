package com.ebis.checkApp.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebis.checkApp.jpa.EmployeeProfiles;
import com.ebis.checkApp.repository.EmployeeProfilesRepository;

@Service
public class EmployeeProfilesService {

    @Autowired
    private EmployeeProfilesRepository employeeProfilesRepository;

    public List<EmployeeProfiles> findAll() {
        return employeeProfilesRepository.findAll();
    }

    public Optional<EmployeeProfiles> findById(Integer userId) {
        return Optional.ofNullable(employeeProfilesRepository.findByUserId(userId));
    }

    public EmployeeProfiles findByUserId(Integer userId) {
        return employeeProfilesRepository.findByUserId(userId);
    }

    public EmployeeProfiles save(EmployeeProfiles profile) {
        return employeeProfilesRepository.save(profile);
    }
    
    
    public EmployeeProfiles createprofile(EmployeeProfiles profile) {
    	profile.setCreatedAt(Timestamp.from(Instant.now()));

        return employeeProfilesRepository.save(profile);
    }
    
    


    public void deleteById(Integer profileId) {
        employeeProfilesRepository.deleteById(profileId);
    }
}
