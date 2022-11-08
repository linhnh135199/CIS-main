package com.example.demo.catalog.controller;

import com.example.demo.app.entity.DiseaseListICD10;
import com.example.demo.catalog.controller.AbstractController;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/catalog_icd10")
public class CatalogIcdController extends AbstractController {

    @GetMapping("/disease_list_icd10/show")
    public String diseaseListIcd10Index(Model model, HttpServletRequest httpServletRequest, Authentication authentication){
        if (authentication != null) {
            return "catalog/icd10/icd10-management";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/disease_list_icd10/add")
    public String addUser(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "catalog/icd10/add-edit-icd10";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/disease_list_icd10/edit")
    public String editUser(HttpServletRequest httpServletRequest, Authentication authentication) {
        if (authentication != null) {
            return "catalog/icd10/add-edit-icd10";
        }
        return "redirect:/login";
    }

}
