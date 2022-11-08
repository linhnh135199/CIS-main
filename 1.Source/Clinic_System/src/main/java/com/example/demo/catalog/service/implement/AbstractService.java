package com.example.demo.catalog.service.implement;

import com.example.demo.catalog.repository.DiseaseListIcd10Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractService {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractService.class);
    @Autowired
    protected DiseaseListIcd10Repository diseaseListIcd10Repository;


}
