package com.example.demo.catalog.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Icd10Request {
    private Long id;

    private String chapter;

    private String chapterCode;

    private String chapterName;

    private String groupNumber;

    private String groupName;

    private String type;

    private String diseaseCode;

    private String diseaseName;

    private String reportCode;
}
