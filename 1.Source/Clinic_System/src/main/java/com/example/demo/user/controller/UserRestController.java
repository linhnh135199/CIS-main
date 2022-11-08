package com.example.demo.user.controller;

import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.vo.UserRequet;
import com.example.demo.user.vo.UserSearchReq;
import com.example.demo.user.vo.UserSearchRes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

	/** The Constant logger. */
	public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	/** The user service. */
	@Autowired
	UserService userService;

	/**
	 * List all users.
	 *
	 * @return the response entity
	 */
	@GetMapping(value = "/getData", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<CommonRes> getData(UserSearchReq userSearchReq) {
		CommonRes commonRes = new CommonRes();
		try {
			UserSearchRes users = userService.getData(userSearchReq);
			commonRes.setData(users);
			commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
			commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
		}catch (Exception e){
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(commonRes);
	}

	@PostMapping(value = "/addEditUser", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<CommonRes> saveUser(UserRequet userRequet) {
		CommonRes commonRes = new CommonRes();
		try {
			commonRes = userService.save(userRequet);
		}catch (Exception e){
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(commonRes);
	}

	/**
	 * Delete user.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@RequestParam("id") Long id) {
		CommonRes commonRes = new CommonRes();
		logger.info("Fetching & Deleting User with id {}", id);
		try {
			User user = userService.findById(id);
			if (user == null) {
				logger.error("Unable to delete. User with id {} not found.", id);
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			userService.deleteUserById(id);
			commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
			commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
		}catch (Exception e){
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(commonRes);
	}
	
	@RequestMapping(value = "/getLoggedUserName", method = RequestMethod.GET)
	public ResponseEntity<User> getLoggedUserName() {
		User user = userService.getloggedInUser();
		if(StringUtils.isEmpty(user.getAvatar())) {
			user.setAvatar("/lib/images/dashboard/avatar.jpg");
		}
	
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping(value = "/getDataById", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<CommonRes> getDataById(@RequestParam("id") Long id) {
		CommonRes commonRes = new CommonRes();
		try {
			User users = userService.findById(id);
			if (users != null) {
				commonRes.setData(users);
			}
			commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
			commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
		}catch (Exception e){
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(commonRes);
	}

	@GetMapping(value = "/getAllDoctor", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<CommonRes> getAllDoctor() {
		CommonRes commonRes = new CommonRes();
		try {
			List<User> users = userService.getAllDoctor();
			commonRes.setData(users);
			commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
			commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
		}catch (Exception e){
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(commonRes);
	}

	@GetMapping(value = "/getDataUserLogin", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<CommonRes> getDataUserLogin() {
		CommonRes commonRes = new CommonRes();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) auth.getPrincipal();
			User users = userService.findById(user.getId());
			if (users != null) {
				commonRes.setData(users);
			}
			commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
			commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
		}catch (Exception e){
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage(e.getMessage());
		}
		return ResponseEntity.ok(commonRes);
	}
}
