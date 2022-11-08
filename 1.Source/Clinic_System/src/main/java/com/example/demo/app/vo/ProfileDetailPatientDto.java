package com.example.demo.app.vo;

import com.example.demo.catalog.request.MedicineReq;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProfileDetailPatientDto {
    Long idPatient;
    String name;
    String code;
    String phone;
    String address;
    Date birthday;
    String gender;
    Double temperature;
    Double height;
    Double bloodPressure;
    Double weight;
    Date dayExamination;
    String symptom;
    String firstDiagnosis;
    String finalDiagnosis;
    String includingDiseases;
    String conclude;
    String doctorName;
    List<MedicineReq> prescriptionReqList;
}
