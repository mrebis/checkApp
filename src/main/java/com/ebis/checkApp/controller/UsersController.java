package com.ebis.checkApp.controller;


import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebis.checkApp.jpa.Users;
import com.ebis.checkApp.service.UsersService;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/users")
public class UsersController {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private UsersService usersService;
    
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer userId) {
        return usersService.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/signup")
	public Users signup(@RequestBody Users user) {	
		
		logger.debug("Signing up, username: {}", user.getUsername());
		return this.usersService.signup(user);
	}
   
    
    
    @PostMapping("/login")
	public ResponseEntity<Users> login(@RequestBody Users user) {
		
		logger.debug("Authenticating, username: {}, password: {}", user.getEmail(), user.getPasswordHash());
			
		/* Spring Security Authentication. */
		user = this.usersService.authenticate(user);

		/* Generate JWT and HTTP Header */
		HttpHeaders jwtHeader = this.usersService.generateJwtHeader(user.getUsername());
					
		logger.debug("User Authenticated, username: {}", user.getUsername());
			
		return new ResponseEntity<>(user, jwtHeader, OK);
	}

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer userId, @RequestBody Users userDetails) {
        return usersService.findById(userId)
                .map(user -> {
                    user.setEmail(userDetails.getEmail());
                    user.setPasswordHash(userDetails.getPasswordHash());
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setRole(userDetails.getRole());
                    user.setCreatedAt(userDetails.getCreatedAt());
                    user.setUpdatedAt(userDetails.getUpdatedAt());
                    Users updatedUser = usersService.save(user);
                    return ResponseEntity.ok(updatedUser);
                }).orElse(ResponseEntity.notFound().build());
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteUser(@PathVariable Integer userId) {
//        return usersService.deleteByUserId(userId)
//                .map(user -> {
//                    usersService.deleteByUserId(userId);
//                    return ResponseEntity.noContent().build();
//                }).orElse(ResponseEntity.notFound().build());
//    }
    
	@GetMapping("/verify/email")
	public void verifyEmail() {
			
		logger.debug("Verifying Email");
			
		this.usersService.verifyEmail();
	}
	
	  @GetMapping("/getuser")
	  public Users getUser() {
				
			logger.debug("Getting User Data");
				
			return this.usersService.getUser();
		}
	  
	  
	  @GetMapping("/all")
	  public List<Users> getAllOtherUsers() {
	        return usersService.getAllOtherUsers();
	    }
	  
	
}
