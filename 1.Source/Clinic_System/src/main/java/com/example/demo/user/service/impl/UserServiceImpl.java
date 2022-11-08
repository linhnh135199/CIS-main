package com.example.demo.user.service.impl;

import com.example.demo.common.CommonRes;
import com.example.demo.common.Constants;
import com.example.demo.common.ErrorCode;
import com.example.demo.common.utils.DateUtils;
import com.example.demo.common.utils.TokenUtils;
import com.example.demo.user.entity.RoleType;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.RoleTypeRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import com.example.demo.user.vo.UserRequet;
import com.example.demo.user.vo.UserSearchReq;
import com.example.demo.user.vo.UserSearchRes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The role type repository. */
	@Autowired
	private RoleTypeRepository roleTypeRepository;

	
	@Autowired
	UserService userService;


	@Override
	public CommonRes save(UserRequet vo) {
		CommonRes commonRes = new CommonRes();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		HashMap<String, String> result = new HashMap<String, String>();
		User user = null;
		if(vo.getId() != null){
			user = userService.findById(vo.getId());
		}else{
			user = userService.findByPhone(vo.getPhone());
			if(user != null && user.getActive() == 1){
				commonRes.setResponseCode(ErrorCode.USER_EXIST.getKey());
				commonRes.setMessage(ErrorCode.USER_EXIST.getValue());
				return commonRes;
			}else{
				user = new User();
			}
		}
		if(StringUtils.isNotEmpty(vo.getPhone())){
			user.setPhoneNumber(vo.getPhone().replaceAll("[^0-9]", ""));
		}
		if(StringUtils.isNotEmpty(vo.getPassword())){
			user.setPassword(passwordEncoder.encode(vo.getPassword()));
		}
		RoleType roleType = null;
		switch (vo.getRole()){
			case "/user/doctor-management":
				roleType = roleTypeRepository.findByName(Constants.USER_ROLE_DOCTOR);
				break;
			case "/user/patient-management":
				roleType = roleTypeRepository.findByName(Constants.USER_ROLE_PATIENT);
				break;
			case "/user/receptionist-management":
				roleType = roleTypeRepository.findByName(Constants.USER_ROLE_RECEPTIONIST);
				break;
			case "/user/admin-management":
				roleType = roleTypeRepository.findByName(Constants.USER_ROLE_ADMIN);
				break;
			default:
				roleType = roleTypeRepository.findByName(Constants.USER_ROLE_USER);
				break;

		}
		if(roleType != null){
			user.setRoleId(roleType.getId());
		}
		user.setName(vo.getFullName());
		user.setGender(vo.getGender());
		user.setActive(1);
		user.setCreatedTime(new Date());
		Date birthDay = DateUtils.stringToDate(vo.getBirthOfDate(), "dd/MM/yyyy");
		user.setBirthday(birthDay);
		user.setEmail(vo.getEmail());
		user.setAddress(vo.getAddress());
		user = userRepository.save(user);
		commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
		commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
		String token = TokenUtils.genarateToken(user.getPhoneNumber(), 3600000);
		result.put("token", token);
		result.put("user_id", Long.toString(user.getId()));
		commonRes.setData(result);
		return commonRes;
	}


	@Override
	public void initAdmin() {
		if (roleTypeRepository.findByName("ROLE_ADMIN") == null) {
			RoleType roleType = new RoleType();
			roleType.setName("ROLE_ADMIN");
			roleTypeRepository.save(roleType);
		}

		if (roleTypeRepository.findByName("ROLE_DOCTOR") == null) {
			RoleType roleType = new RoleType();
			roleType.setName("ROLE_DOCTOR");
			roleTypeRepository.save(roleType);
		}

		if (roleTypeRepository.findByName("ROLE_RECEPTIONIST") == null) {
			RoleType roleType = new RoleType();
			roleType.setName("ROLE_RECEPTIONIST");
			roleTypeRepository.save(roleType);
		}

		if (userRepository.findByPhoneNumber("0123456789") == null) {
			RoleType roleType = roleTypeRepository.findByName(Constants.USER_ROLE_ADMIN);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			User admin = new User();
			admin.setEmail("admin@gmail.com");
			admin.setPassword(passwordEncoder.encode("12345678"));
			admin.setName("System Admin");
			admin.setPhoneNumber("0123456789");
			if(roleType != null){
				admin.setRoleId(roleType.getId());
			}
			admin.setBirthday(new Date());
			admin.setGender("Nam");
			admin.setActive(1);
			admin.setCreatedTime(new Date());
			admin.setModifiedTime(new Date());
			userRepository.save(admin);
		}

		if (userRepository.findByPhoneNumber("1234") == null) {
			RoleType roleType = roleTypeRepository.findByName(Constants.USER_ROLE_DOCTOR);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			User admin = new User();
			admin.setEmail("doctor@gmail.com");
			admin.setPassword(passwordEncoder.encode("12345678"));
			admin.setName("Doctor Test");
			admin.setPhoneNumber("1234");
			if(roleType != null){
				admin.setRoleId(roleType.getId());
			}
			admin.setBirthday(new Date());
			admin.setGender("Nam");
			admin.setActive(1);
			admin.setCreatedTime(new Date());
			admin.setModifiedTime(new Date());
			userRepository.save(admin);
		}

	}

	@Override
	public User findById(Long id) {
		User user = new User();
		Optional<User> opUser = userRepository.findById(id);
		if (opUser.isPresent()) {
			user = opUser.get();
		}
		return user;
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User getloggedInUser() {
		User user = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			user = findByEmail(((UserDetails) principal).getUsername());
		}
		return user;
	}

	@Override
	public User findByPhone(String phone) {
		return userRepository.findByPhoneNumber(phone);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singleton(new SimpleGrantedAuthority(role));
	}

	@Override
	public UserSearchRes getData(UserSearchReq userSearchReq) {
		UserSearchRes userSearchRes = new UserSearchRes();
		int page = 1;
		if(userSearchReq.getPage() != null){
			page = userSearchReq.getPage();
		}
		int offset = page - 1;
		Page<User> users = null;
		Pageable pageable = PageRequest.of(offset, Constants.PAGE_SIZE);
		RoleType roleType = null;
		if(StringUtils.isNotEmpty(userSearchReq.getRole())){
			switch (userSearchReq.getRole()) {
				case "/user/doctor-management":
					roleType = roleTypeRepository.findByName(Constants.USER_ROLE_DOCTOR);
					users = userRepository.getAllByRoleIdAndKey(roleType.getId(), userSearchReq.getKey(), pageable);
					break;
				case "/user/patient-management":
					users = userRepository.getAllPatientIdAndKey(userSearchReq.getKey(),pageable);
					break;
				case "/user/receptionist-management":
					roleType = roleTypeRepository.findByName(Constants.USER_ROLE_RECEPTIONIST);
					users = userRepository.getAllByRoleIdAndKey(roleType.getId(), userSearchReq.getKey(),pageable);
					break;
				case "/user/admin-management":
					roleType = roleTypeRepository.findByName(Constants.USER_ROLE_ADMIN);
					users = userRepository.getAllByRoleIdAndKey(roleType.getId(), userSearchReq.getKey(),pageable);
					break;
				default:
					return userSearchRes;
			}
		}
		if(users != null){
			userSearchRes.setListUser(users.getContent());
			userSearchRes.setTotal(users.getTotalElements());
		}
		return userSearchRes;
	}

	@Override
	public void setUserAuthen(User user) {
		Optional<RoleType> roleType = roleTypeRepository.findById(user.getRoleId());
		UsernamePasswordAuthenticationToken
				authentication = new UsernamePasswordAuthenticationToken(user, roleType.get().getName());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
	public List<User> getAllDoctor() {
		List<User> users = null;
		RoleType roleType = null;
		roleType = roleTypeRepository.findByName(Constants.USER_ROLE_DOCTOR);
		users = userRepository.getAllDoctor(roleType.getId());
		return users;
	}
}
