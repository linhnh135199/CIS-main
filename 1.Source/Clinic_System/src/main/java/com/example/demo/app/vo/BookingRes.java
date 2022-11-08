package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BookingRes {
    Long total;
    List<BookingDto> bookingDtos;
}
