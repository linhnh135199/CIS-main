package com.example.demo.app.service;

import com.example.demo.app.entity.Patient;
import com.example.demo.app.vo.PatientDetailDto;
import com.example.demo.app.vo.PatientReq;
import com.example.demo.app.vo.ProfileDetailPatientDto;
import com.example.demo.app.vo.ProfilePatientDto;
import com.example.demo.common.CommonRes;
import com.example.demo.user.vo.UserSearchReq;

public interface PatientService {

    PatientDetailDto getPatientById(Long id);

    void updatePatient(PatientDetailDto updatePatient);

    Patient findById(Long id);

    CommonRes getPatient(PatientReq patientReq);

    CommonRes getPatientRecord(Long id);

    ProfilePatientDto getProfilePatientById(Long id);

    CommonRes getPaymentPatientRecord(UserSearchReq userSearchReq);

    void getPaymentPatientChangeStatus(Long id);

    CommonRes getPaymentPatientRecordById(Long id);

    ProfileDetailPatientDto getProfilePatientDetailByPatientId(Long id);
}
