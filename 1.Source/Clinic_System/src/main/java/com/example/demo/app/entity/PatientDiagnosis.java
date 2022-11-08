package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = Constants.TABLE_PATIENT_DIAGNOSIS)
@Getter
@Setter
public class PatientDiagnosis extends BasicEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "patientId")
    @JsonProperty
    private Long patientId;

    @Column(name = "diseaseListICD10Id")
    @JsonProperty
    private Long diseaseListICD10Id;

}
