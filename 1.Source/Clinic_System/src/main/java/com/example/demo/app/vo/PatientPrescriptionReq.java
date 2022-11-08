package com.example.demo.app.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties
public class PatientPrescriptionReq {
    Long idPatient;
    List<PrescriptionReq> prescriptionReqList;
}
