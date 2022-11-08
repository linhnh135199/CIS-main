package com.example.demo.catalog.service;

import com.example.demo.app.entity.DiseaseListICD10;
import com.example.demo.catalog.vo.Icd10Request;
import com.example.demo.catalog.vo.Icd10SearchReq;
import com.example.demo.catalog.vo.Icd10SearchRes;
import com.example.demo.common.CommonRes;
import com.example.demo.user.entity.User;
import com.example.demo.user.vo.UserRequet;
import com.example.demo.user.vo.UserSearchReq;
import com.example.demo.user.vo.UserSearchRes;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiseaseListIcd10Service {
    List<DiseaseListICD10> getAll();

    DiseaseListICD10 getById(long id);

    /**
     * Find by id.
     *
     * @param id the id
     * @return the catalog
     */
    DiseaseListICD10 findById(Long id);

    void deleteIcdById(Long id);

    List<DiseaseListICD10> getAllData();

    Icd10SearchRes getData(Icd10SearchReq icd10SearchReq);

    CommonRes save(Icd10Request icd10Request);

    DiseaseListICD10 findByName(String diseaseName);
}
