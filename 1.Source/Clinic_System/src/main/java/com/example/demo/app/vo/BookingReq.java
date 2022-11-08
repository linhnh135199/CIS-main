package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingReq {
    Long id;
    Long patientId;
    String code;
    String fullName;
    String birthday;
    String address;
    String dateAdmission;
    String hourAdmission;
    String gender;
    String phone;
    String email;
    String nameCompanion;
    String phoneCompanion;
    String companionRelationship;
    String description;
    Long roomId;
    String status;
    Boolean priority;
}
