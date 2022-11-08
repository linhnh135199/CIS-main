package com.example.demo.catalog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/catalog_medicine")
public class CatalogMedicineController extends AbstractController {

    @GetMapping("/medicine_master/show")
    public String medicineIndex( Model model, Authentication authentication){
        if (authentication != null) {
            return "catalog/medicine/medicine-management";
        }
        return "redirect:/login";
    }
}
