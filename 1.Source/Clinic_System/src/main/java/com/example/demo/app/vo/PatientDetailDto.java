package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientDetailDto {
    Long id;
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
    Long firstDiagnosisId;
    Long finalDiagnosisId;
    String includingDiseasesId;
    String firstDiagnosis;
    String finalDiagnosis;
    String includingDiseases;
    String conclude;
}
