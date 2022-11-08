package com.example.demo.app.service;

import com.example.demo.app.entity.AdmissionRecord;
import com.example.demo.app.entity.ExaminationRoom;
import com.example.demo.app.entity.RoomCategory;
import com.example.demo.app.vo.AdmissionRecordRes;
import com.example.demo.app.vo.BookingReq;
import com.example.demo.app.vo.BookingRes;
import com.example.demo.app.vo.PatientReq;

import java.util.List;

public interface AdmissionRecordService {
    List<AdmissionRecordRes> getAllAdmissionRecordByDoctorCurDate();

    void updateStatusAdmissionOfDoctor(Long id, String status);

    void booking(BookingReq bookingReq);

    BookingRes getAllAdmission(PatientReq patientReq);

    BookingReq getBookingById(Long id);

    BookingReq getBookingByPhone(String phone);

    void updateStatusBooking(BookingReq bookingReq);

    List<RoomCategory> getRoom(String date);
}
