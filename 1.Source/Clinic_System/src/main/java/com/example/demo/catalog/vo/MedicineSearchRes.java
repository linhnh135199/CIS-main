package com.example.demo.catalog.vo;

import com.example.demo.app.entity.MedicineMaster;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class MedicineSearchRes {
    private Long total;

    private List<MedicineMaster> listMedicine;
}
