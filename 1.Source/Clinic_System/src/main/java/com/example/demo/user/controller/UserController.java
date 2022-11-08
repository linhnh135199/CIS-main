package com.example.demo.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest httpServletRequest, Authentication authentication) {
		if(authentication != null){
			SecurityContext context = SecurityContextHolder.getContext();
			context.setAuthentication(null);
			SecurityContextHolder.clearContext();
		}
		return "redirect:/homepage";
	}

	@RequestMapping(value = "/doctor-management")
	public String doctorManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/user-management";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/patient-management")
	public String patientManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/user-management";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/receptionist-management")
	public String receptionistManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/user-management";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/admin-management")
	public String adminManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/user-management";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/add-management")
	public String addUser(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/add-edit-user";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/edit-management")
	public String editUser(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/add-edit-user";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/add-schedule_management")
	public String addSchedule(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/add-edit-user";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/view-schedule_management")
	public String viewSchedule(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/add-edit-user";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/payment-management")
	public String paymentManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "app/payment-management";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/update-profile-user")
	public String updateProfileUser(HttpServletRequest httpServletRequest, Authentication authentication) {
		if (authentication != null) {
			return "user/update-profile-user";
		}
		return "redirect:/login";
	}

}
