package com.example.demo.app.service.Impl;

import com.example.demo.app.entity.AdmissionRecord;
import com.example.demo.app.entity.ExaminationRoom;
import com.example.demo.app.entity.Patient;
import com.example.demo.app.entity.RoomCategory;
import com.example.demo.app.repository.AdmissionRecordRepository;
import com.example.demo.app.repository.ExaminationRoomRepository;
import com.example.demo.app.repository.PatientRepository;
import com.example.demo.app.repository.RoomCategoryRepository;
import com.example.demo.app.service.AdmissionRecordService;
import com.example.demo.app.vo.*;
import com.example.demo.common.Constants;
import com.example.demo.common.utils.DateUtils;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdmissionRecordServiceImpl implements AdmissionRecordService {
    @Autowired
    AdmissionRecordRepository admissionRecordRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ExaminationRoomRepository examinationRoomRepository;

    @Autowired
    RoomCategoryRepository roomCategoryRepository;

    @Override
    public List<AdmissionRecordRes> getAllAdmissionRecordByDoctorCurDate() {
        List<AdmissionRecordRes> admissionRecordRes = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
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
        List<AdmissionRecord> admissionRecords = admissionRecordRepository.findAllByDoctorCurDate(user.getId(), startDate, endDate);
        if(!CollectionUtils.isEmpty(admissionRecords)){
            for (AdmissionRecord admissionRecord : admissionRecords){
                AdmissionRecordRes recordRes = new AdmissionRecordRes();
                recordRes.setId(admissionRecord.getId());
                Optional<Patient> patient = patientRepository.findById(admissionRecord.getPatientID());
                if(patient.isPresent()){
                    Optional<User> userP = userRepository.findById(patient.get().getUserID());
                    if(userP.isPresent()) {
                        recordRes.setName(userP.get().getName());
                    }
                }
                recordRes.setPriority(admissionRecord.getPriority());
                recordRes.setStatus(admissionRecord.getStatus());
                recordRes.setIdPatient(admissionRecord.getPatientID());
                admissionRecordRes.add(recordRes);
            }
        }
        return admissionRecordRes;
    }

    @Override
    public void updateStatusAdmissionOfDoctor(Long id, String status) {
        List<AdmissionRecord> admissionRecord = admissionRecordRepository.findAllByPatientID(id);
        if (admissionRecord != null){
            AdmissionRecord record = admissionRecord.get(0);
            record.setStatus(status);
            record.setModifiedTime(new Date());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            record.setModifiedUser(user.getName());
            admissionRecordRepository.save(record);
        }
    }

    @Override
    public void booking(BookingReq bookingReq) {
        User user = userRepository.getByPhoneNumberAndName(bookingReq.getFullName(), bookingReq.getPhone());
        if(user != null){
            user.setModifiedTime(new Date());
        }else{
            user = new User();
            user.setCreatedTime(new Date());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode("user123"));

            if(StringUtils.isNotEmpty(bookingReq.getGender())){
                user.setGender(bookingReq.getGender());
            }
            if(StringUtils.isNotEmpty(bookingReq.getEmail())){
                user.setEmail(bookingReq.getEmail());
            }
            if(StringUtils.isNotEmpty(bookingReq.getBirthday())){
                Date birthDay = DateUtils.stringToDate(bookingReq.getBirthday(), "dd/MM/yyyy");
                user.setBirthday(birthDay);
            }
            if(StringUtils.isNotEmpty(bookingReq.getFullName())){
                user.setName(bookingReq.getFullName());
            }
            if(StringUtils.isNotEmpty(bookingReq.getAddress())){
                user.setAddress(bookingReq.getAddress());
            }
            if(StringUtils.isNotEmpty(bookingReq.getPhone())){
                user.setPhoneNumber(bookingReq.getPhone());
            }
        }
        user.setActive(1);
        user = userRepository.save(user);

        // tạo bệnh án
        Patient patient = new Patient();
        patient.setUserID(user.getId());
        patient.setCreatedTime(new Date());
        if(StringUtils.isNotEmpty(bookingReq.getNameCompanion())){
            patient.setCompanionName(bookingReq.getNameCompanion());
        }
        if(StringUtils.isNotEmpty(bookingReq.getPhoneCompanion())){
            patient.setCompanionPhone(bookingReq.getPhoneCompanion());
        }
        if(StringUtils.isNotEmpty(bookingReq.getCompanionRelationship())){
            patient.setCompanionRelationship(bookingReq.getCompanionRelationship());
        }
        if(StringUtils.isNotEmpty(bookingReq.getDateAdmission())){
            Date dayExamination = DateUtils.stringToDate(bookingReq.getDateAdmission(), "dd/MM/yyyy");
            patient.setDayExamination(dayExamination);
        }
        patient = patientRepository.save(patient);

        // tạo lịch hẹn
        AdmissionRecord admissionRecord = new AdmissionRecord();
        admissionRecord.setCreatedTime(new Date());
        admissionRecord.setPatientID(patient.getId());
        admissionRecord.setPriority(false);
        admissionRecord.setStatus(Constants.STATUS_WAITING);
        if(StringUtils.isNotEmpty(bookingReq.getDateAdmission())){
            Date dayExamination = DateUtils.stringToDate(bookingReq.getDateAdmission(), "dd/MM/yyyy");
            admissionRecord.setAdmissionDate(dayExamination);
        }
        admissionRecord.setStatusApprove(Constants.STATUS_WAITING);
        admissionRecord.setExaminationReason(bookingReq.getDescription());
        admissionRecord.setAdmissionHour(bookingReq.getHourAdmission());
        if(bookingReq.getRoomId() != null){
            Optional<ExaminationRoom> examinationRoom = examinationRoomRepository.findById(bookingReq.getRoomId());
            if(examinationRoom.isPresent()){
                admissionRecord.setDoctorId(examinationRoom.get().getDoctorId());
                admissionRecord.setStatusApprove(Constants.STATUS_CONFIRM);
            }
        }
        admissionRecordRepository.save(admissionRecord);
    }

    @Override
    public BookingRes getAllAdmission(PatientReq patientReq) {
        BookingRes bookingRes = new BookingRes();
        int page = 1;
        if(patientReq.getPage() != null){
            page = patientReq.getPage();
        }
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, Constants.PAGE_SIZE);
        Page<AdmissionRecord> admissionRecords = admissionRecordRepository.getAllAdmission(patientReq.getKey(), pageable);
        if(admissionRecords != null){
            List<AdmissionRecord> admissions = admissionRecords.getContent();
            List<BookingDto> bookingDtos = new ArrayList<>();
            for (AdmissionRecord admissionRecord: admissions){
                BookingDto bookingDto = new BookingDto();
                bookingDto.setId(admissionRecord.getId());
                bookingDto.setStatusApprove(admissionRecord.getStatusApprove());
                bookingDto.setAdmissionDate(admissionRecord.getAdmissionDate());
                Optional<Patient> patient = patientRepository.findById(admissionRecord.getPatientID());
                if(patient.isPresent()){
                    Optional<User> userP = userRepository.findById(patient.get().getUserID());
                    if(userP.isPresent()) {
                        bookingDto.setPhone(userP.get().getPhoneNumber());
                        bookingDto.setFullName(userP.get().getName());
                    }
                }
                bookingDtos.add(bookingDto);
            }
            bookingRes.setBookingDtos(bookingDtos);
            bookingRes.setTotal(admissionRecords.getTotalElements());
        }
        return bookingRes;
    }

    @Override
    public BookingReq getBookingById(Long id) {
        BookingReq bookingReq = null;
        Optional<AdmissionRecord> admissionRecord = admissionRecordRepository.findById(id);
        if(admissionRecord.isPresent()){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            bookingReq = new BookingReq();
            bookingReq.setId(id);
            Optional<Patient> patient = patientRepository.findById(admissionRecord.get().getPatientID());
            if(patient.isPresent()){
                Optional<User> userP = userRepository.findById(patient.get().getUserID());
                if(userP.isPresent()) {
                    bookingReq.setFullName(userP.get().getName());
                    bookingReq.setPhone(userP.get().getPhoneNumber());
                    bookingReq.setCode(userP.get().getId()+"");
                    bookingReq.setBirthday(dateFormat.format(userP.get().getBirthday()));
                    bookingReq.setEmail(userP.get().getEmail());
                    bookingReq.setAddress(userP.get().getAddress());
                    bookingReq.setGender(userP.get().getGender());
                }
                bookingReq.setNameCompanion(patient.get().getCompanionName());
                bookingReq.setPhoneCompanion(patient.get().getCompanionPhone());
                bookingReq.setCompanionRelationship(patient.get().getCompanionRelationship());
            }
            bookingReq.setHourAdmission(admissionRecord.get().getAdmissionHour());
            bookingReq.setDateAdmission(dateFormat.format(admissionRecord.get().getAdmissionDate()));
            bookingReq.setDescription(admissionRecord.get().getExaminationReason());
            bookingReq.setPatientId(admissionRecord.get().getPatientID());
            bookingReq.setStatus(admissionRecord.get().getStatusApprove());
            bookingReq.setPriority(admissionRecord.get().getPriority());

            if(admissionRecord.get().getDoctorId() != null){
                Date startDate = admissionRecord.get().getAdmissionDate();
                startDate.setHours(0);
                startDate.setMinutes(0);
                startDate.setSeconds(0);
                Date endDate = admissionRecord.get().getAdmissionDate();
                endDate.setHours(23);
                endDate.setMinutes(59);
                endDate.setSeconds(59);
                ExaminationRoom examinationRoom = examinationRoomRepository.getByDateAndDoctor(
                        admissionRecord.get().getDoctorId(), startDate, endDate);
                if(examinationRoom != null){
                    bookingReq.setRoomId(examinationRoom.getId());
                }
            }

        }
        return bookingReq;
    }

    @Override
    public BookingReq getBookingByPhone(String phone) {
        BookingReq bookingReq = null;
        User user = userRepository.findByPhoneNumber(phone);
        if(user != null){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            bookingReq = new BookingReq();
            bookingReq.setBirthday(dateFormat.format(user.getBirthday()));
            bookingReq.setPhone(phone);
            bookingReq.setAddress(user.getAddress());
            bookingReq.setEmail(user.getEmail());
            bookingReq.setGender(user.getGender());
            bookingReq.setFullName(user.getName());
        }
        return bookingReq;
    }

    @Override
    public void updateStatusBooking(BookingReq bookingReq) {
        if(bookingReq.getId() != null){
            Optional<AdmissionRecord> admissionRecord = admissionRecordRepository.findById(bookingReq.getId());
            if(admissionRecord.isPresent()){

                // update bệnh án
                Optional<Patient> patient = patientRepository.findById(bookingReq.getPatientId());
                if(patient.isPresent()){
                    Optional<User> users = userRepository.findById(patient.get().getUserID());
                    if(users.isPresent()){
                        User user = users.get();
                        if(StringUtils.isNotEmpty(bookingReq.getGender())){
                            user.setGender(bookingReq.getGender());
                        }
                        if(StringUtils.isNotEmpty(bookingReq.getEmail())){
                            user.setEmail(bookingReq.getEmail());
                        }
                        if(StringUtils.isNotEmpty(bookingReq.getBirthday())){
                            Date birthDay = DateUtils.stringToDate(bookingReq.getBirthday(), "dd/MM/yyyy");
                            user.setBirthday(birthDay);
                        }
                        if(StringUtils.isNotEmpty(bookingReq.getFullName())){
                            user.setName(bookingReq.getFullName());
                        }
                        if(StringUtils.isNotEmpty(bookingReq.getAddress())){
                            user.setAddress(bookingReq.getAddress());
                        }
                        if(StringUtils.isNotEmpty(bookingReq.getPhone())){
                            user.setPhoneNumber(bookingReq.getPhone());
                        }
                        user.setModifiedTime(new Date());
                        user.setActive(1);
                        user = userRepository.save(user);
                    }
                    if(StringUtils.isNotEmpty(bookingReq.getNameCompanion())){
                        patient.get().setCompanionName(bookingReq.getNameCompanion());
                    }
                    if(StringUtils.isNotEmpty(bookingReq.getPhoneCompanion())){
                        patient.get().setCompanionPhone(bookingReq.getPhoneCompanion());
                    }
                    if(StringUtils.isNotEmpty(bookingReq.getCompanionRelationship())){
                        patient.get().setCompanionRelationship(bookingReq.getCompanionRelationship());
                    }
                    if(StringUtils.isNotEmpty(bookingReq.getDateAdmission())){
                        Date dayExamination = DateUtils.stringToDate(bookingReq.getDateAdmission(), "dd/MM/yyyy");
                        patient.get().setDayExamination(dayExamination);
                    }
                    if(StringUtils.isNotEmpty(bookingReq.getFullName())){
                        patient.get().setNamePatient(bookingReq.getFullName());
                    }
                    patientRepository.save(patient.get());
                }
                // update lịch hẹn
                AdmissionRecord admissionRecord1 = admissionRecord.get();
                if(StringUtils.isNotEmpty(bookingReq.getDateAdmission())){
                    Date dayExamination = DateUtils.stringToDate(bookingReq.getDateAdmission(), "dd/MM/yyyy");
                    admissionRecord1.setAdmissionDate(dayExamination);
                }
                admissionRecord1.setAdmissionHour(bookingReq.getHourAdmission());
                admissionRecord1.setExaminationReason(bookingReq.getDescription());
                admissionRecord1.setAdmissionHour(bookingReq.getHourAdmission());
                admissionRecord1.setPriority(bookingReq.getPriority());
                if(bookingReq.getRoomId() != null){
                    Optional<ExaminationRoom> examinationRoom = examinationRoomRepository.findById(bookingReq.getRoomId());
                    if(examinationRoom.isPresent()){
                        admissionRecord1.setDoctorId(examinationRoom.get().getDoctorId());
                        admissionRecord1.setStatusApprove(Constants.STATUS_CONFIRM);
                    }
                }
                if(bookingReq.getStatus().equalsIgnoreCase(Constants.STATUS_CANCEL)){
                    admissionRecord1.setStatusApprove(Constants.STATUS_CANCEL);
                } else if(bookingReq.getStatus().equalsIgnoreCase(Constants.STATUS_RECEIVE)){
                    admissionRecord1.setStatusApprove(Constants.STATUS_RECEIVE);
                }else if(bookingReq.getStatus().equalsIgnoreCase(Constants.STATUS_CONFIRM)){
                    admissionRecord1.setStatusApprove(Constants.STATUS_CONFIRM);
                }else{
                    admissionRecord1.setStatusApprove(Constants.STATUS_WAITING);
                }
                admissionRecordRepository.save(admissionRecord1);
            }
        }else{
            booking(bookingReq);
        }

    }

    @Override
    public List<RoomCategory> getRoom(String date) {
        List<RoomCategory> rooms = new ArrayList<>();
        if(StringUtils.isNotEmpty(date)){
            Date startDate = DateUtils.stringToDate(date, "dd/MM/yyyy");
            startDate.setHours(0);
            startDate.setMinutes(0);
            startDate.setSeconds(0);
            Date endDate = DateUtils.stringToDate(date, "dd/MM/yyyy");
            endDate.setHours(23);
            endDate.setMinutes(59);
            endDate.setSeconds(59);
            List<ExaminationRoom> examinationRooms = examinationRoomRepository.getByDateAndNotDoctor(startDate, endDate);
            for (ExaminationRoom examinationRoom : examinationRooms){
                Optional<RoomCategory> roomCategory = roomCategoryRepository.findById(examinationRoom.getRoomCategoryId());
                if (roomCategory.isPresent()){
                    RoomCategory roomCategory1 = roomCategory.get();
                    roomCategory1.setId(examinationRoom.getId());
                    rooms.add(roomCategory.get());
                }
            }
        }else{
            rooms = (List<RoomCategory>) roomCategoryRepository.findAll();
        }
        return rooms;
    }
}
