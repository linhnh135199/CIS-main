package com.example.demo.user.service;

import com.example.demo.common.CommonRes;
import com.example.demo.user.entity.User;
import com.example.demo.user.vo.LoginRequest;
import com.example.demo.user.vo.UserRequet;
import com.example.demo.user.vo.UserSearchReq;
import com.example.demo.user.vo.UserSearchRes;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface UserService {

	/**
	 * Save.
	 *
	 * @param user the user
	 * @return the user
	 */
	CommonRes save(UserRequet user);


	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the user
	 */
	User findById(Long id);


	/**
	 * Delete user by id.
	 *
	 * @param id the id
	 */
	void deleteUserById(Long id);

	/**
	 * Inits the admin.
	 */
	void initAdmin();

	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	List<User> getAllUser();

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the user
	 */
	User findByEmail(String email);

	
	User getloggedInUser();
	
	User findByPhone(String phone);

	Collection<? extends GrantedAuthority> getAuthorities(String role);

	UserSearchRes getData(UserSearchReq userSearchReq);

	void setUserAuthen(User user);

	List<User> getAllDoctor();
}
