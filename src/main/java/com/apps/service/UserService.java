package com.apps.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.apps.entity.User;

@Service
public interface UserService {

	String signin(String username, String password);

	String signup(User user);

	void delete(int id);

	List<User> findAllUser();

	User whoami(HttpServletRequest request);

	String refresh(String username);
}
