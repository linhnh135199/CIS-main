package com.example.demo.app.controller;

import com.example.demo.app.entity.Regimen;
import com.example.demo.app.service.CMSRegimenService;
import com.example.demo.app.vo.MedicineDto;
import com.example.demo.app.vo.RegimenDto;
import com.example.demo.app.vo.RegimenReq;
import com.example.demo.app.vo.RegimenRes;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/api/regimen")
public class RegimenRestController {

    @Autowired
    private CMSRegimenService regimenService;

    @GetMapping(value = "/getRegimen", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getRegimen(RegimenReq regimenReq) {
        CommonRes commonRes = new CommonRes();
        try {
            RegimenRes regimenRes = regimenService.getRegimen(regimenReq);
            commonRes.setData(regimenRes);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getRegimenById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getRegimenById(@RequestParam("id") Long id, @RequestParam("type") String type) {
        CommonRes commonRes = new CommonRes();
        try {
            if(type.equalsIgnoreCase("service")){
                RegimenDto regimenDto = regimenService.getRegimenById(id);
                commonRes.setData(regimenDto);
            }
            if(type.equalsIgnoreCase("medicine")){
                MedicineDto medicineDto = regimenService.getRegimenMedicineById(id);
                commonRes.setData(medicineDto);
            }
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/addEditRegimen", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> addEditRegimen(RegimenReq regimenReq) {
        CommonRes commonRes = new CommonRes();
        try {
            regimenService.addEditRegimen(regimenReq);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getRegimenAllPrescription", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getRegimenAllPrescription() {
        CommonRes commonRes = new CommonRes();
        try {
            List<Regimen> regimenRes = regimenService.getRegimenAllPrescription();
            commonRes.setData(regimenRes);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }
}
