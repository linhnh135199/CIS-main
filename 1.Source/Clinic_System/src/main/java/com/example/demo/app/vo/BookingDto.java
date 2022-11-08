package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookingDto {
    Long id;
    String fullName;
    String phone;
    Date admissionDate;
    String statusApprove;
}
