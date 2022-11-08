package com.example.demo.app.controller;

import com.example.demo.app.service.PatientService;
import com.example.demo.app.vo.PatientDetailDto;
import com.example.demo.app.vo.PatientReq;
import com.example.demo.app.vo.ProfileDetailPatientDto;
import com.example.demo.app.vo.ProfilePatientDto;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import com.example.demo.user.vo.UserSearchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/patient")
public class PatientRestController {

    @Autowired
    PatientService patientService;

    @GetMapping(value = "/getPatientById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPatientById(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            PatientDetailDto patient = patientService.getPatientById(id);
            if (patient != null) {
                commonRes.setData(patient);
            }
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/updatePatient", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> updatePatient(PatientDetailDto patientDetailDto) {
        CommonRes commonRes = new CommonRes();
        try {
            patientService.updatePatient(patientDetailDto);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/getPatientList", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPatientList(PatientReq patientReq) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes = patientService.getPatient(patientReq);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getPatientRecord", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPatientRecord(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes = patientService.getPatientRecord(id);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getProfilePatientById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getProfilePatientById(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            ProfilePatientDto patient = patientService.getProfilePatientById(id);
            if (patient != null) {
                commonRes.setData(patient);
            }
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getPaymentPatientRecord", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPaymentPatientRecord(UserSearchReq userSearchReq) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes = patientService.getPaymentPatientRecord(userSearchReq);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getPaymentPatientRecordById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPaymentPatientRecordById(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes = patientService.getPaymentPatientRecordById(id);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/getPaymentPatientChangeStatus", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getPaymentPatientChangeStatus(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            patientService.getPaymentPatientChangeStatus(id);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getProfilePatientDetailByPatientId", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getProfilePatientDetailByPatientId(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            ProfileDetailPatientDto patient = patientService.getProfilePatientDetailByPatientId(id);
            if (patient != null) {
                commonRes.setData(patient);
            }
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }
}
