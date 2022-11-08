package com.example.demo.app.service.Impl;

import com.example.demo.app.entity.AdmissionRecord;
import com.example.demo.app.entity.ExaminationRoom;
import com.example.demo.app.entity.RoomCategory;
import com.example.demo.app.repository.AdmissionRecordRepository;
import com.example.demo.app.repository.ExaminationRoomRepository;
import com.example.demo.app.repository.RoomCategoryRepository;
import com.example.demo.app.service.ExaminationRoomService;
import com.example.demo.app.vo.CalenderDoctorDto;
import com.example.demo.app.vo.DoctorCalenderReq;
import com.example.demo.common.CommonRes;
import com.example.demo.common.ErrorCode;
import com.example.demo.common.utils.DateUtils;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminationRoomServiceImpl implements ExaminationRoomService {

    @Autowired
    private ExaminationRoomRepository examinationRoomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private AdmissionRecordRepository admissionRecordRepository;

    @Override
    public List<CalenderDoctorDto> getCalenderDoctor(String date) {
        List<CalenderDoctorDto> calenderDoctorDtos = new ArrayList<>();
        Date startDate = DateUtils.stringToDate(date, "dd/MM/yyyy");
        startDate.setHours(0);
        startDate.setMinutes(0);
        startDate.setSeconds(0);
        Date endDate = DateUtils.stringToDate(date, "dd/MM/yyyy");
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
        List<ExaminationRoom> examinationRooms = examinationRoomRepository.getCalenderDoctor(startDate, endDate);
        for (ExaminationRoom examinationRoom: examinationRooms){
            CalenderDoctorDto calenderDoctorDto = new CalenderDoctorDto();
            if(examinationRoom.getRoomCategoryId() != null){
                Optional<RoomCategory> roomCategory = roomCategoryRepository.findById(examinationRoom.getRoomCategoryId());
                if(roomCategory.isPresent()){
                    calenderDoctorDto.setRoomName(roomCategory.get().getName());
                }
            }
            User user = userService.findById(examinationRoom.getDoctorId());
            calenderDoctorDto.setFullName(user.getName());
            calenderDoctorDto.setHourAdmission(examinationRoom.getHourAdmission());
            calenderDoctorDto.setNum(examinationRoom.getDate().getDay());
            calenderDoctorDto.setId(examinationRoom.getId());
            calenderDoctorDtos.add(calenderDoctorDto);
        }
        return calenderDoctorDtos;
    }

    @Override
    public CommonRes addCalenderDoctor(DoctorCalenderReq doctorCalenderReq) {
        CommonRes commonRes = new CommonRes();
        Date date = DateUtils.stringToDate(doctorCalenderReq.getDate(), "dd/MM/yyyy");
        Date startDate = DateUtils.stringToDate(doctorCalenderReq.getDate(), "dd/MM/yyyy");
        startDate.setHours(0);
        startDate.setMinutes(0);
        startDate.setSeconds(0);
        Date endDate = DateUtils.stringToDate(doctorCalenderReq.getDate(), "dd/MM/yyyy");
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
        ExaminationRoom examinationRoom = null;
        List<ExaminationRoom> examinationRooms = examinationRoomRepository.getByDateAndRoom(
                startDate, endDate, doctorCalenderReq.getRoomId(), doctorCalenderReq.getHourAdmission());
        if (examinationRooms != null && examinationRooms.size() > 0){
            commonRes.setResponseCode(ErrorCode.ERROR_ADD_DOCTOR_CALENDER.getKey());
            commonRes.setMessage(ErrorCode.ERROR_ADD_DOCTOR_CALENDER.getValue());
        }else{
            examinationRoom = new ExaminationRoom();
            examinationRoom.setDoctorId(doctorCalenderReq.getDoctorId());
            examinationRoom.setHourAdmission(doctorCalenderReq.getHourAdmission());
            examinationRoom.setRoomCategoryId(doctorCalenderReq.getRoomId());
            examinationRoom.setDate(date);
            examinationRoom.setCreatedTime(new Date());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            examinationRoom.setCreatedUser(user.getName());
            examinationRoomRepository.save(examinationRoom);
            commonRes.setResponseCode(ErrorCode.PROCESS_SUCCESS.getKey());
            commonRes.setMessage(ErrorCode.PROCESS_SUCCESS.getValue());
        }
        return commonRes;
    }

    @Override
    public int deleteCalenderDoctor(Long id) {
        Optional<ExaminationRoom> examinationRoom = examinationRoomRepository.findById(id);
        if(examinationRoom.isPresent()){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date startDate = calendar.getTime();
            Date endDate = Calendar.getInstance().getTime();
            startDate.setHours(23);
            startDate.setMinutes(59);
            startDate.setSeconds(59);
            endDate.setHours(23);
            endDate.setMinutes(59);
            endDate.setSeconds(59);
            List<AdmissionRecord> admissionRecords = admissionRecordRepository.findAllByDoctorCurDate(
                    examinationRoom.get().getDoctorId(), startDate, endDate);
            if(admissionRecords.size() > 0){
                return 0;
            }
            examinationRoomRepository.delete(examinationRoom.get());
        }
        return 1;
    }
}
