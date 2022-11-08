package com.example.demo.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping(value = "/doctor")
public class AdmissionRecordController {

    @RequestMapping(value = "/list-patient-management")
    public String listPatientManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/list-patient-management";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/list-route-management")
    public String listRouteManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/list-regimen-management";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/list-clinical-management")
    public String profilePatientManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/profile-patient-management";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/confirm-form-management")
    public String confirmFormManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/commitment-management";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/calendar-management")
    public String calendarManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/calendar-doctor-management";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/add-regimen-service")
    public String addRegimenService(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/add-regimen-service";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/edit-regimen-service")
    public String editRegimenService(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/add-regimen-service";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/add-regimen-medicine")
    public String addRegimenMedicine(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/add-regimen-medicine";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/edit-regimen-medicine")
    public String editRegimenMedicine(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/add-regimen-medicine";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/booking-management")
    public String bookingManagement(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "app/booking-management";
        }
        return "redirect:/login";
    }
}
