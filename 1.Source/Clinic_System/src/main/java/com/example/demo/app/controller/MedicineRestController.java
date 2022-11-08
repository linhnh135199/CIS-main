package com.example.demo.app.controller;

import com.example.demo.app.entity.MedicineMaster;
import com.example.demo.app.service.MedicineService;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/medicine")
public class MedicineRestController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping(value = "/getDataSearch", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPatientList(@RequestParam("key") String key) {
        CommonRes commonRes = new CommonRes();
        try {
            if (StringUtils.isNotEmpty(key)){
                key = key.trim();
            }
            List<MedicineMaster> medicines = medicineService.getDataByName(key);
            commonRes.setData(medicines);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getDataById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getDataById(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            MedicineReq medicines = medicineService.getDataById(id);
            commonRes.setData(medicines);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }
}
