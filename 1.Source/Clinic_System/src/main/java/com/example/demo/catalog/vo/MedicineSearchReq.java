package com.example.demo.catalog.vo;

import lombok.*;

@Setter
@Getter
@ToString
public class MedicineSearchReq {
    private Integer pageIndex;
    private Integer pageSize;
}
