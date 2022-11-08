package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_PATIENT)
@Getter
@Setter
public class Patient extends BasicEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    @JsonProperty
    private Long userID;

    @Column(name = "namePatient")
    @JsonProperty
    private String namePatient;

    @Column(name = "temperature")
    @JsonProperty
    private Double temperature;

    @Column(name = "height")
    @JsonProperty
    private Double height;

    @Column(name = "blood_pressure")
    @JsonProperty
    private Double bloodPressure;

    @Column(name = "weight")
    @JsonProperty
    private Double weight;

    @Column(name = "day_examination")
    @JsonProperty
    private Date dayExamination;

    @Column(name = "symptom")
    @JsonProperty
    private String symptom;

    @Column(name = "including_diseases")
    @JsonProperty
    private String includingDiseases;

    @Column(name = "conclude")
    @JsonProperty
    private String conclude;

    @Column(name = "companion_name")
    @JsonProperty
    private String companionName;

    @Column(name = "companion_relationship")
    @JsonProperty
    private String companionRelationship;

    @Column(name = "companion_phone")
    @JsonProperty
    private String companionPhone;

    @Column(name = "statusPayment")
    @JsonProperty
    private String statusPayment;

    @Column(name = "first_diagnosis_id")
    @JsonProperty
    private Long firstDiagnosisId;

    @Column(name = "final_diagnosis_id")
    @JsonProperty
    private Long finalDiagnosisId;

}
