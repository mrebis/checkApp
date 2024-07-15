package com.ebis.checkApp.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;

import com.ebis.checkApp.exception.domain.EmailExistException;
import com.ebis.checkApp.exception.domain.EmailNotVerifiedException;
import com.ebis.checkApp.exception.domain.UserNotFoundException;
import com.ebis.checkApp.exception.domain.UsernameExistException;
import com.ebis.checkApp.jpa.Users;
import com.ebis.checkApp.provider.ResourceProvider;
import com.ebis.checkApp.repository.UsersRepository;
import com.ebis.checkApp.security.JwtService;


@Service
public class UsersService {
	
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	EmailService emailService;
	
	

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	ResourceProvider provider;
	
	@Autowired
	TemplateEngine templateEngine;
		
	@Autowired
	JavaMailSender javaMailSender;	

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Optional<Users> findById(Integer userId) {
        return usersRepository.findByUserId(userId);
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users save(Users user) {
        return usersRepository.save(user);
    }

    private void updateValue(Supplier<String> getter, Consumer<String> setter) {

		Optional.ofNullable(getter.get())
				// .filter(StringUtils::hasText)
				.map(String::trim).ifPresent(setter);
	}
    
public Users signup(Users user) {
		


		user.setUsername(user.getUsername().toLowerCase());
		user.setEmail(user.getEmail().toLowerCase());
		this.validateUsernameAndEmail(user.getUsername(), user.getEmail());
		user.setEmailVerified(false);
		user.setPasswordHash(this.passwordEncoder.encode(user.getPasswordHash()));
		user.setCreatedAt(Timestamp.from(Instant.now()));

		this.emailService.sendVerificationEmail(user);
		this.usersRepository.save(user);
		return user;
	}
	
	
	
	
	
		
		private void validateUsernameAndEmail(String username, String email) {
		
			this.usersRepository.findByUsername(username).ifPresent(u -> {
				throw new UsernameExistException(String.format("Username already exists, %s", u.getUsername()));
			});
		
			this.usersRepository.findByEmail(email).ifPresent(u -> {
				throw new EmailExistException(String.format("Email already exists, %s", u.getEmail()));
			});
		
		}

	public void verifyEmail() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Users user = this.usersRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));

		user.setEmailVerified(true);

		this.usersRepository.save(user);
	}


	private static Users isEmailVerified(Users user) {

		if (user.getEmailVerified().equals(false)) {
			throw new EmailNotVerifiedException(String.format("Email requires verification, %s", user.getEmail()));
		}

		return user;
	}

	private Authentication authenticate(String username, String passwordHash) {
		return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, passwordHash));
	}

	public Users authenticate(Users user) {

		/* Spring Security Authentication. */
		this.authenticate(user.getUsername(), user.getPasswordHash());

		/* Get User from the DB. */
		return this.usersRepository.findByUsername(user.getUsername()).map(UsersService::isEmailVerified).get();
	}


	public HttpHeaders generateJwtHeader(String username) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(AUTHORIZATION, this.jwtService.generateJwtToken(username, this.provider.getJwtExpiration()));

		return headers;
	}

	public void sendResetPasswordEmail(String email) {

		Optional<Users> opt = this.usersRepository.findByEmail(email);

		if (opt.isPresent()) {
			this.emailService.sendResetPasswordEmail(opt.get());
		} else {
			logger.debug("Email doesn't exist, {}", email);
		}
	}

	public void resetPassword(String password) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Users user = this.usersRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));

		user.setPasswordHash(this.passwordEncoder.encode(password));

		this.usersRepository.save(user);
	}

	public Users getUser() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		/* Get User from the DB. */
		return this.usersRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
	}

	private void updatePassword(Supplier<String> getter, Consumer<String> setter) {

		Optional.ofNullable(getter.get()).filter(StringUtils::hasText).map(this.passwordEncoder::encode)
				.ifPresent(setter);
	}

	private Users updateUser(Users user, Users currentUser) {

		this.updateValue(user::getFirstName, currentUser::setFirstName);
		this.updateValue(user::getLastName, currentUser::setLastName);
		this.updateValue(user::getEmail, currentUser::setEmail);
		this.updatePassword(user::getPasswordHash, currentUser::setPasswordHash);

		return this.usersRepository.save(currentUser);
	}

	public Users updateUser(Users user) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		/* Validates the new email if provided */
		this.usersRepository.findByEmail(user.getEmail()).filter(u -> !u.getUsername().equals(username)).ifPresent(u -> {
			throw new EmailExistException(String.format("Email already exists, %s", u.getEmail()));
		});

		/* Get and Update User */
		return this.usersRepository.findByUsername(username).map(currentUser -> this.updateUser(user, currentUser))
				.orElseThrow(() -> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
	}

	public Users getUserById(int userId) {
		return usersRepository.findById(userId).orElse(null);
	}

	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}
	
	
	   public List<Users> getAllOtherUsers() {
	        String username = SecurityContextHolder.getContext().getAuthentication().getName();

	        Users user = this.usersRepository.findByUsername(username)
	                .orElseThrow(() -> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));

	        return this.usersRepository.findByUserIdNot(user.getUserId());
	    }

	public void deleteUser(int userId) {
		usersRepository.deleteByUserId(userId);
	}

	public Users updateUser(int userId, Users updatedUser) {
		Users existingUser = usersRepository.findById(userId).orElse(null);
		if (existingUser != null) {
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setPasswordHash(updatedUser.getPasswordHash());
			existingUser.setFirstName(updatedUser.getFirstName());
			existingUser.setLastName(updatedUser.getLastName());
			existingUser.setUsername(updatedUser.getUsername());
			existingUser.setEmailVerified(updatedUser.getEmailVerified());
			existingUser.setCreatedAt(updatedUser.getCreatedAt());
			return usersRepository.save(existingUser);
		}
		return null;
	}

	public void deleteByUserId(Integer userId) {
		// TODO Auto-generated method stub
		
	}

	

	


	
	



}
