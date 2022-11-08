package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_CLINICAL_RECORD)
@Getter
@Setter
public class ClinicalRecord extends BasicEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "patient_id")
    @JoinColumn
    private Long patientID;

    @Column(name = "examination_date")
    @JsonProperty
    private Date examinationDate;

    @Column(name = "temperature")
    @JsonProperty
    private String temperature;

    @Column(name = "bloodPressure")
    @JsonProperty
    private String bloodPressure;

    @Column(name = "height")
    @JsonProperty
    private String height;

    @Column(name = "weight")
    @JsonProperty
    private String weight;

    @Column(name = "symptom")
    @JsonProperty
    private String symptom;

    @Column(name = "ICD10_id")
    @JoinColumn
    private Long icd10ID;

    @Column(name = "conclusion")
    @JsonProperty
    private String conclusion;

}
