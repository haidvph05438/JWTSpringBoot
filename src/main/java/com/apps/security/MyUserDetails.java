package com.apps.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apps.entity.User;
import com.apps.repository.UserRepository;

/**
 * @author doanhai
 *
 */
@Service
public class MyUserDetails implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}
		return org.springframework.security.core.userdetails.User.withUsername(username).accountExpired(false)
				.accountLocked(false).disabled(false).credentialsExpired(false).password(user.getPassword())
				.authorities(user.getRoles()).build();
	}

}
