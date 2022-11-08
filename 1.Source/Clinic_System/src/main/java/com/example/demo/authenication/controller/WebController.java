package com.example.demo.authenication.controller;

import com.example.demo.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebController {

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	@RequestMapping(value = "/login")
	public String getLogin() {
		return "authenication/login";
	}

//	/**
//	 * Gets the sign up.
//	 *
//	 * @return the sign up
//	 */
//	@RequestMapping(value = "/register")
//	public String getSignUp() {
//		return "authenication/signup";
//	}

	/**
	 * Access denied.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/403")
	public String accessDenied() {
		return "common/403";
	}

	@RequestMapping(value = "/homepage")
	public String homepage(){
		return "common/homepage";
	}

	@RequestMapping(value = "/appointment")
	public String appointment(){
		return "common/appointment";
	}

	@RequestMapping(value = "/appointmentBooking")
	public String appointmentBooking(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "common/appointment";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/")
	public String index() {
		return "common/homepage";
	}

	@RequestMapping(value = "/admin")
	public String home(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			User user = (User) authentication.getPrincipal();
			if(user.getRoleId() == 2){
				return "app/list-patient-management";
			}else if(user.getRoleId() == 3){
				return "app/booking-management";
			}else if(user.getRoleId() == 1){
				return "app/calendar-doctor-management";
			}
			return "home-admin";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/forgot-password")
	public String forgotPassword() {
		return "authenication/forgot-password";
	}

}
