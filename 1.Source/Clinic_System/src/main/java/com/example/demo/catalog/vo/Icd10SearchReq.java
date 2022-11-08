package com.example.demo.catalog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Icd10SearchReq {
    private String key;
    private Integer page;
}
