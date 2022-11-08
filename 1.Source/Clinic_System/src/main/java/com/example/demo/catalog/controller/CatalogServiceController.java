package com.example.demo.catalog.controller;
import com.example.demo.catalog.dto.ServiceDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/catalog_service")
    public class CatalogServiceController extends AbstractController {
        @GetMapping("/service_master/show")
        public String technicalIndex(Model model, Authentication authentication) {
            if (authentication != null) {
                return "catalog/service/service-management";
            }
            return "redirect:/login";
        }
}
