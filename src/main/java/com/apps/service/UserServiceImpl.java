package com.apps.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apps.entity.User;
import com.apps.repository.UserRepository;
import com.apps.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public String signin(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	}

	@Override
	public String signup(User user) {
		if (userRepository.findByUsername(user.getUsername()) == null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		}
		return null;
	}

	@Override
	public void delete(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> findAllUser() {

		return userRepository.findAll();
	}

	@Override
	public User whoami(HttpServletRequest req) {
		return userRepository.findByUsername(jwtTokenProvider.getUserNameToToken(jwtTokenProvider.resolveToken(req)));
	}

	@Override
	public String refresh(String username) {
		return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	}

}
