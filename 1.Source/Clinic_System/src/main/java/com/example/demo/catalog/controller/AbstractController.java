package com.example.demo.catalog.controller;

import com.example.demo.catalog.service.DiseaseListIcd10Service;
import com.example.demo.catalog.service.MedicineService;
import com.example.demo.catalog.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {
    @Autowired
    protected DiseaseListIcd10Service diseaseListIcd10Service;
    @Autowired
    protected MedicineService medicineService;
    @Autowired
    protected ServiceService serviceService;
}
