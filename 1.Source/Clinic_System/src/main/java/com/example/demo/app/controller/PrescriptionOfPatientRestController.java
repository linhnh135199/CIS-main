package com.example.demo.app.controller;

import com.example.demo.app.entity.MedicineMaster;
import com.example.demo.app.service.PrescriptionOfPatientService;
import com.example.demo.app.vo.PatientPrescriptionReq;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/prescription")
public class PrescriptionOfPatientRestController {

    @Autowired
    private PrescriptionOfPatientService prescriptionOfPatientService;

    @GetMapping(value = "/getPrescriptionOfPatientById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPrescriptionOfPatientById(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            List<MedicineReq> patients = prescriptionOfPatientService.getPrescriptionOfPatientById(id);
            commonRes.setData(patients);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(path = "/updatePrescriptionToPatient", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CommonRes> updatePrescriptionToPatient(@RequestBody PatientPrescriptionReq patientPrescriptionReq) {
        CommonRes commonRes = new CommonRes();
        try {
            prescriptionOfPatientService.updatePrescriptionToPatient(patientPrescriptionReq);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getPrescriptionByRegimen", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPrescriptionByRegimen(@RequestParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            List<MedicineReq> patients = prescriptionOfPatientService.getPrescriptionByRegimen(id);
            commonRes.setData(patients);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }
}
