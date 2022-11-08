package com.example.demo.authenication.controller;

import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import com.example.demo.common.utils.PasswordUtils;
import com.example.demo.common.utils.TokenUtils;
import com.example.demo.user.entity.RoleType;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import com.example.demo.user.vo.LoginRequest;
import com.example.demo.user.vo.UserRequet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenController {

	/** The Constant logger. */
	public static final Logger logger = LoggerFactory.getLogger(AuthenController.class);

	/** The user service. */
	@Autowired
	UserService userService;

	@PostMapping(value = "/login", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<CommonRes> login(LoginRequest userLoginVO) {
		CommonRes commonRes = new CommonRes();
		if (userLoginVO == null) {
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage("Phone or Password can not exists");
			return ResponseEntity.ok(commonRes);
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		User user = userService.findByPhone(userLoginVO.getPhone());
		if (user == null) {
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage("Incorrect Phone");
			return ResponseEntity.ok(commonRes);
		}
		if (user.getActive() == 0) {
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage("User is not active");
			return ResponseEntity.ok(commonRes);
		}
		if (!passwordEncoder.matches(userLoginVO.getPassword(), user.getPassword())) {
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage("Incorrect Password");
			return ResponseEntity.ok(commonRes);
		}

		LoginRequest data = new LoginRequest();
		data.setPhone(user.getPhoneNumber());

		// generateToken can config Expiration from masterdata
		HashMap<String, String> result = new HashMap<String, String>();
		String token = TokenUtils.genarateToken(data, 24*60*60*60*1000);
		result.put("user_id", Long.toString(user.getId()));
		result.put("token", token);

		// Nếu người dùng hợp lệ, set thông tin cho Seturity Context
		userService.setUserAuthen(user);

		commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
		commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
		commonRes.setData(result);
		return ResponseEntity.ok(commonRes);
	}

	@PostMapping(value = "/forgot-password", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<CommonRes> forgotPassword(LoginRequest userLoginVO) {
		CommonRes commonRes = new CommonRes();
		if (userLoginVO == null) {
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage("Phone can not exists");
			return ResponseEntity.ok(commonRes);
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		User user = userService.findByPhone(userLoginVO.getPhone());
		if (user == null) {
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage("Incorrect Phone");
			return ResponseEntity.ok(commonRes);
		}
		if (user.getActive() == 0) {
			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
			commonRes.setMessage("User is not active");
			return ResponseEntity.ok(commonRes);
		}
		String pass = PasswordUtils.generatePassayPassword();
		user.setPassword(passwordEncoder.encode(pass));

		commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
		commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
		return ResponseEntity.ok(commonRes);
	}

//	@PostMapping(value = "/register", consumes = {MediaType.ALL_VALUE})
//	public ResponseEntity<CommonRes> register(UserRequet vo) {
//		CommonRes commonRes = new CommonRes();
//		try {
//			commonRes = userService.save(vo);
//		}catch (Exception e){
//			commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
//			commonRes.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getValue());
//		}
//		return ResponseEntity.ok(commonRes);
//	}

}
