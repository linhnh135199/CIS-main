package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentRes {
    Long total;
    List<PaymentDto> paymentDtos;
}
