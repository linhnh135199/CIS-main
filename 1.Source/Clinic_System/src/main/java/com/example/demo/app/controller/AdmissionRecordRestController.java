package com.example.demo.app.controller;

import com.example.demo.app.entity.ExaminationRoom;
import com.example.demo.app.entity.RoomCategory;
import com.example.demo.app.service.AdmissionRecordService;
import com.example.demo.app.service.ExaminationRoomService;
import com.example.demo.app.vo.*;
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

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/admission-record")
public class AdmissionRecordRestController {
    @Autowired
    private AdmissionRecordService admissionRecordService;

    @Autowired
    private ExaminationRoomService examinationRoomService;

    @GetMapping(value = "/getAdmissionOfDoctor", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getAdmissionOfDoctor() {
        CommonRes commonRes = new CommonRes();
        try {
            List<AdmissionRecordRes> admissionRecords = admissionRecordService.getAllAdmissionRecordByDoctorCurDate();
            if (!admissionRecords.isEmpty()) {
                commonRes.setData(admissionRecords);
            }
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/updateStatusAdmissionOfDoctor", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> updateStatusAdmissionOfDoctor(AdmissionRecordUpdateReq admissionRecordUpdateReq) {
        CommonRes commonRes = new CommonRes();
        try {
            admissionRecordService.updateStatusAdmissionOfDoctor(admissionRecordUpdateReq.getId(), admissionRecordUpdateReq.getStatus());
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getAllBooking", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getAllAdmission(PatientReq patientReq) {
        CommonRes commonRes = new CommonRes();
        try {
            BookingRes admissionRecords = admissionRecordService.getAllAdmission(patientReq);
            commonRes.setData(admissionRecords);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getBookingById", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getBookingById(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            BookingReq admissionRecords = admissionRecordService.getBookingById(id);
            commonRes.setData(admissionRecords);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getBookingByPhone", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getBookingByPhone(@PathParam("phone") String phone) {
        CommonRes commonRes = new CommonRes();
        try {
            BookingReq admissionRecords = admissionRecordService.getBookingByPhone(phone);
            commonRes.setData(admissionRecords);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/updateStatusBooking", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> updateStatusBooking(BookingReq bookingReq) {
        CommonRes commonRes = new CommonRes();
        try {
            admissionRecordService.updateStatusBooking(bookingReq);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getRoom", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getRoom(@PathParam("date") String date) {
        CommonRes commonRes = new CommonRes();
        try {
            List<RoomCategory> examinationRooms = admissionRecordService.getRoom(date);
            commonRes.setData(examinationRooms);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @GetMapping(value = "/getCalenderDoctor", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> getCalenderDoctor(@Param("date") String date) {
        CommonRes commonRes = new CommonRes();
        try {
            List<CalenderDoctorDto> calenderDoctorDtos = examinationRoomService.getCalenderDoctor(date);
            commonRes.setData(calenderDoctorDtos);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/addCalenderDoctor", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> addCalenderDoctor(DoctorCalenderReq doctorCalenderReq) {
        CommonRes commonRes = new CommonRes();
        try {
            commonRes = examinationRoomService.addCalenderDoctor(doctorCalenderReq);
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }

    @PostMapping(value = "/deleteCalenderDoctor", consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<CommonRes> deleteCalenderDoctor(@PathParam("id") Long id) {
        CommonRes commonRes = new CommonRes();
        try {
            int num = examinationRoomService.deleteCalenderDoctor(id);
            if(num == 1){
                commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
                commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
            }else{
                commonRes.setResponseCode(ErrorCode.ERROR_DELETE_BOOKING.getKey());
                commonRes.setMessage(ErrorCode.ERROR_DELETE_BOOKING.getValue());
            }
        }catch (Exception e){
            commonRes.setResponseCode(ErrorCode.INTERNAL_SERVER_ERROR.getKey());
            commonRes.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(commonRes);
    }
}
