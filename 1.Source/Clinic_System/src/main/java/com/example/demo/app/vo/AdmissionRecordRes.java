package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdmissionRecordRes {
    Long id;
    Long idPatient;
    String name;
    Boolean priority;
    String status;
}
