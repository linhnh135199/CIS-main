package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = Constants.TABLE_PRESCRIPTION_OF_PATIENT)
@Getter
@Setter
public class PrescriptionOfPatient extends BasicEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "patientId")
    @JsonProperty
    private Long patientId;

    @Column(name = "medicineId")
    @JsonProperty
    private Long medicineId;

    @Column(name = "doctorId")
    @JsonProperty
    private Long doctorId;

    @Column(name = "huongDan")
    @JsonProperty
    private String huongDan;

    @Column(name = "donVi")
    @JsonProperty
    private String donVi;

    @Column(name = "soLuong")
    @JsonProperty
    private Integer soLuong;
}
