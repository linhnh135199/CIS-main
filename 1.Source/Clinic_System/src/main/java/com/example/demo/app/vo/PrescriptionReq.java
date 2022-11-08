package com.example.demo.app.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionReq {
    Long id;
    String huongDan;
    String donVi;
    Integer soLuong;
}
