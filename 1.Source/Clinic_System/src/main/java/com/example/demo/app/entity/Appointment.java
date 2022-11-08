package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_APPOINTMENT)
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "a_id", nullable = false)
    @JsonProperty
    private Long id;

    @Column(name = "patient_id", nullable = true)
    @JsonProperty
    private Long patientID;

}
