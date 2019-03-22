package com.apps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apps.entity.User;
import com.apps.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/signin")
	public String signin(@RequestBody User user) {

		return userService.signin(user.getUsername(), user.getPassword());
	}

	@PostMapping("/signup")
	public String signup(@RequestBody User user) {

		return userService.signup(user);
	}

	//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/users")
	public List<User> findList() {

		return userService.findAllUser();
	}

	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/users/{id}")
	public List<User> deleteUser(@PathVariable("id") int id) {
		userService.delete(id);
		return userService.findAllUser();
	}

	//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/me")
	public User whoami(HttpServletRequest request) {
		return userService.whoami(request);
	}

	//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/refresh/{username}")
	public String refresh(@PathVariable("username") String username) {
		return userService.refresh(username);
	}
}
