package com.apps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apps.entity.User;

/**
 * @author doanhai
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
}
