package com.ebis.checkApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebis.checkApp.jpa.EmployeeProfiles;

@Repository
public interface EmployeeProfilesRepository extends JpaRepository<EmployeeProfiles, Integer> {
    EmployeeProfiles findByUserId(Integer userId);

}
