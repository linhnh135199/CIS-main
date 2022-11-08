package com.example.demo.catalog.service.implement;

import com.example.demo.app.entity.DiseaseListICD10;
import com.example.demo.catalog.repository.DiseaseListIcd10Repository;
import com.example.demo.catalog.service.DiseaseListIcd10Service;
import com.example.demo.catalog.vo.Icd10Request;
import com.example.demo.catalog.vo.Icd10SearchReq;
import com.example.demo.catalog.vo.Icd10SearchRes;
import com.example.demo.common.CommonRes;
import com.example.demo.common.Constants;
import com.example.demo.common.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class DiseaseListIcd10ServiceImplement extends AbstractService implements DiseaseListIcd10Service {

    @Autowired
    private DiseaseListIcd10Repository diseaseListIcd10Repository;

    @Autowired
    private DiseaseListIcd10Service diseaseListIcd10Service;

    @Override
    public List<DiseaseListICD10> getAll() {
        try {
            return (List<DiseaseListICD10>) diseaseListIcd10Repository.findAll();
        } catch (Exception e){
            logger.error("get all with error : {} | {}", e.getMessage(), e.getMessage().toString());
        }
        return null;
    }

    @Override
    public DiseaseListICD10 getById(long id) {
        try {
            Optional<DiseaseListICD10> optional = diseaseListIcd10Repository.findById(id);
            if(optional.isPresent())
                return optional.get();
        }catch (Exception e){
            logger.error("get by id with error : {} | {}", e.getMessage(), e.getMessage().toString());
        }
        return null;
    }

    @Override
    public DiseaseListICD10 findById(Long id) {
        DiseaseListICD10 diseaseListICD10 = new DiseaseListICD10();
        Optional<DiseaseListICD10> opIcd10 = diseaseListIcd10Repository.findById(id);
        if (opIcd10.isPresent()) {
            diseaseListICD10 = opIcd10.get();
        }
        return diseaseListICD10;
    }

    @Override
    public void deleteIcdById(Long id) {
        diseaseListIcd10Repository.deleteById(id);
    }

    @Override
    public List<DiseaseListICD10> getAllData() {
        try {
            return (List<DiseaseListICD10>) diseaseListIcd10Repository.findAll();
        } catch (Exception e){
            logger.error("get all with error : {} | {}", e.getMessage(), e.getMessage().toString());
        }
        return null;
    }

    @Override
    public Icd10SearchRes getData(Icd10SearchReq icd10SearchReq) {
        Icd10SearchRes icd10SearchRes = new Icd10SearchRes();
        int page = 1;
        if(icd10SearchReq.getPage() != null){
            page = icd10SearchReq.getPage();
        }
        int offset = page - 1;
        Page<DiseaseListICD10> icd10s = null;
        Pageable pageable = PageRequest.of(offset, Constants.PAGE_SIZE);
        icd10s = diseaseListIcd10Repository.getAllByName(icd10SearchReq.getKey(), pageable);

        if(icd10s != null){
            icd10SearchRes.setListICD(icd10s.getContent());
            icd10SearchRes.setTotal(icd10s.getTotalElements());
        }
        return icd10SearchRes;
    }

    @Override
    public CommonRes save(Icd10Request icd10Request) {
        CommonRes commonRes = new CommonRes();
        HashMap<String, String> result = new HashMap<String, String>();
        DiseaseListICD10 diseaseListICD10 = null;
        if(icd10Request.getId() != null){
            diseaseListICD10 = diseaseListIcd10Service.findById(icd10Request.getId());
        } else {
            diseaseListICD10 = diseaseListIcd10Service.findByName(icd10Request.getDiseaseName());
            if(diseaseListICD10 != null){
                commonRes.setResponseCode(ErrorCode.USER_EXIST.getKey());
                commonRes.setMessage(ErrorCode.USER_EXIST.getValue());
                return commonRes;
            }else{
                diseaseListICD10 = new DiseaseListICD10();
            }
        }
        diseaseListICD10.setChapter(icd10Request.getChapter());
        diseaseListICD10.setChapterCode(icd10Request.getChapterCode());
        diseaseListICD10.setChapterName(icd10Request.getChapterName());
        diseaseListICD10.setGroupNumber(icd10Request.getGroupNumber());
        diseaseListICD10.setGroupName(icd10Request.getGroupName());
        diseaseListICD10.setType(icd10Request.getType());
        diseaseListICD10.setDiseaseCode(icd10Request.getDiseaseCode());
        diseaseListICD10.setDiseaseName(icd10Request.getDiseaseName());
        diseaseListICD10.setReportCode(icd10Request.getReportCode());
        diseaseListICD10 = diseaseListIcd10Repository.save(diseaseListICD10);
        commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
        commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        result.put("id", Long.toString(diseaseListICD10.getId()));
        commonRes.setData(result);
        return commonRes;
    }

    @Override
    public DiseaseListICD10 findByName(String diseaseName) {
        return diseaseListIcd10Repository.findByDiseaseName(diseaseName);
    }
}
