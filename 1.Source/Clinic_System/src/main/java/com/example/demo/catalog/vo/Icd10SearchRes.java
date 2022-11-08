package com.example.demo.catalog.vo;

import com.example.demo.app.entity.DiseaseListICD10;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class Icd10SearchRes {
    private Long total;
    private List<DiseaseListICD10> listICD;
}
