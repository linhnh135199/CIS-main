package com.example.demo.app.vo;

import com.example.demo.catalog.request.MedicineReq;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicineDto {
    Long id;
    String type;
    Long specializedId;
    String specialized;
    String name;
    String status;
    String createUser;
    List<MedicineReq> medicines;
}
