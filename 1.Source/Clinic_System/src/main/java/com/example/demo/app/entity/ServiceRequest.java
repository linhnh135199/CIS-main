package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_SERVICE_REQUEST)
@Getter
@Setter
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sr_id", nullable = false)
    @JsonProperty
    private Long id;

    @Column(name = "clinical_record_id")
    @JoinColumn
    private Long clinicalRecordID;

    @Column(name = "service_id")
    @JoinColumn
    private Long serviceID;

    @Column(name = "instruction_note")
    @JsonProperty
    private String instructionNote;

    @Column(name = "unit")
    @JsonProperty
    private String unit;

}
