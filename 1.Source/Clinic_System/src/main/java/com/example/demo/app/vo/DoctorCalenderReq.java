package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DoctorCalenderReq {
    Long doctorId;
    String date;
    String hourAdmission;
    Long roomId;
}
