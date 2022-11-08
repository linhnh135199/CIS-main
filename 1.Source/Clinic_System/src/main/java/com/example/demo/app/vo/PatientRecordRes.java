package com.example.demo.app.vo;

import com.example.demo.app.entity.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PatientRecordRes {
    Long id;
    String name;
    String code;
    String phone;
    String address;
    Date birthday;
    String gender;
    List<Patient> patients;
}
