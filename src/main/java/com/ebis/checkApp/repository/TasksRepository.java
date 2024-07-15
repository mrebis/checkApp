package com.ebis.checkApp.repository;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebis.checkApp.jpa.Tasks;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
	List<Tasks> findByAssignedToUserId(Integer userId);
    List<Tasks> findByAssignedByUserId(Integer userId);
}
