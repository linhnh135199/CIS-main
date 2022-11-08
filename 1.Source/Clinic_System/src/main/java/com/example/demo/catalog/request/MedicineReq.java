package com.example.demo.catalog.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MedicineReq{

    private Long id;

    private String name;

    private String maHoatChat;

    private String hoatChat;

    private String hamLuong;

    private String maDuongDung;

    private String duongDung;

    private String soDangKy;

    private String price;

    private String dongGoi;

    private String hangSX;

    private String nuocSX;

    private String huongDan;

    private String donVi;

    private Integer soLuong;

    private Date date;

}
