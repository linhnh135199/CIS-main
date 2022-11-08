package com.example.demo.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/patient")
public class PatientController {

    @RequestMapping(value = "/patient-detail-management")
    public String listPatientManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/patient-detail-management";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/patient-detail")
    public String listPatient(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/patient-profile";
        }
        return "redirect:/login";
    }
}
