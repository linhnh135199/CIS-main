package com.example.demo.app.controller;

import com.example.demo.app.service.AdmissionRecordService;
import com.example.demo.app.vo.AdmissionRecordRes;
import com.example.demo.app.vo.AdmissionRecordUpdateReq;
import com.example.demo.app.vo.BookingReq;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingRestController {

    @Autowired
    private AdmissionRecordService admissionRecordService;

    @PostMapping(value = "/add", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> addAdmission(BookingReq bookingReq) {
        CommonRes commonRes = new CommonRes();
        try {
            admissionRecordService.booking(bookingReq);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }
}
