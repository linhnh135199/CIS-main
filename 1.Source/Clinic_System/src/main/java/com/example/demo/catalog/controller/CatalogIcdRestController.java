package com.example.demo.catalog.controller;

import com.example.demo.app.entity.DiseaseListICD10;
import com.example.demo.catalog.vo.Icd10Request;
import com.example.demo.catalog.vo.Icd10SearchReq;
import com.example.demo.catalog.vo.Icd10SearchRes;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import com.example.demo.catalog.service.DiseaseListIcd10Service;
import com.example.demo.user.vo.UserRequet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogIcd")
public class CatalogIcdRestController {
    public static final Logger logger = LoggerFactory.getLogger(CatalogIcdRestController.class);

    @Autowired
    DiseaseListIcd10Service diseaseListIcd10Service;

    @GetMapping(value = "/getDataICD", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getData(Icd10SearchReq icd10SearchReq) {
        CommonRes commonRes = new CommonRes();
        try {
            Icd10SearchRes icd10s = diseaseListIcd10Service.getData(icd10SearchReq);
            commonRes.setData(icd10s);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/disease_list_icd10/getDataById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getDataById(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            DiseaseListICD10 diseaseListICD10 = diseaseListIcd10Service.findById(id);
            if (diseaseListICD10 != null) {
                commonRes.setData(diseaseListICD10);
            }
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/addEditICD", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> saveICD(Icd10Request icd10Request) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes = diseaseListIcd10Service.save(icd10Request);
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @RequestMapping(value = "/deleteICD", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteIcd(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        logger.info("Fetching & Deleting Icd with id {}", id);
        try {
            DiseaseListICD10 diseaseListICD10 = diseaseListIcd10Service.findById(id);
            if (diseaseListICD10 == null) {
                logger.error("Unable to delete. Icd with id {} not found.", id);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            diseaseListIcd10Service.deleteIcdById(id);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.FAIL.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }
}
