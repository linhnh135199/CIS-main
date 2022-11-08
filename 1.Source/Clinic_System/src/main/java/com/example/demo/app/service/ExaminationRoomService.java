package com.example.demo.app.service;

import com.example.demo.app.vo.CalenderDoctorDto;
import com.example.demo.app.vo.DoctorCalenderReq;
import com.example.demo.common.CommonRes;

import java.util.List;

public interface ExaminationRoomService {
    List<CalenderDoctorDto> getCalenderDoctor(String date);
    CommonRes addCalenderDoctor(DoctorCalenderReq doctorCalenderReq);
    int deleteCalenderDoctor(Long id);
}
