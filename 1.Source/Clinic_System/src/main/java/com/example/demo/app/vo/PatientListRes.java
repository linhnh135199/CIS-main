package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatientListRes {
    Long total;
    List<PatientDetailDto> patientDetailDtos;
}
