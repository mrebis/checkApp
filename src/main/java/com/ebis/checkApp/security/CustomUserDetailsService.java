package com.ebis.checkApp.security;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ebis.checkApp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.ebis.checkApp.jpa.Users;
import java.util.Optional;


@Service("UserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UsersRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Users> opt = this.userRepository.findByUsername(username);

		if (opt.isEmpty()) {
			throw new UsernameNotFoundException("Username not found: " + username);
		}

		return new CustomUserDetails(opt.get());
	}

}