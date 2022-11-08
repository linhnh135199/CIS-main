package com.example.demo.app.vo;

import com.example.demo.catalog.request.ServiceRes;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PaymentDto {
    Long idPatient;
    String code;
    String fullName;
    String phone;
    Date admissionDate;
    String statusPayment;
    String gender;
    Integer old;
    String address;
    List<ServiceRes> services;
}
