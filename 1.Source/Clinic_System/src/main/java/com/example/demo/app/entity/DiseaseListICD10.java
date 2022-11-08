package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_DISEASE_LIST_ICD10)
@Getter
@Setter
public class DiseaseListICD10 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icd10_id", nullable = false)
    @JsonProperty
    private Long id;

    @Column(name = "chapter")
    @JsonProperty
    private String chapter;

    @Column(name = "chapter_code")
    @JsonProperty
    private String chapterCode;

    @Column(name = "chapter_name")
    @JsonProperty
    private String chapterName;

    @Column(name = "group_number")
    @JsonProperty
    private String groupNumber;

    @Column(name = "group_name")
    @JsonProperty
    private String groupName;

    @Column(name = "type")
    @JsonProperty
    private String type;

    @Column(name = "disease_code")
    @JsonProperty
    private String diseaseCode;

    @Column(name = "disease_name")
    @JsonProperty
    private String diseaseName;

    @Column(name = "report_code")
    @JsonProperty
    private String reportCode;

}
