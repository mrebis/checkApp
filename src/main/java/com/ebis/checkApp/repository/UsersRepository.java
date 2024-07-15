package com.ebis.checkApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebis.checkApp.jpa.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByUserId(Long userid);
	Optional<Users> findByUserId(Integer integer);
	
	Optional<Users> findByUsername(String username);
	 Optional<Users> findByEmail(String email);
	void deleteByUserId(int userId);
	Optional<Users> findById(int userId);
    List<Users> findByUserIdNot(Integer userId);


    
    
	 
}