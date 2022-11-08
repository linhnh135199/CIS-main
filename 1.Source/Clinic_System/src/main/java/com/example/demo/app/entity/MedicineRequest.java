package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_MEDICINE_REQUEST)
@Getter
@Setter
public class MedicineRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mr_id", nullable = false)
    @JsonProperty
    private Long id;

    @Column(name = "clinical_record_id")
    @JoinColumn
    private Long clinicalRecordID;

    @Column(name = "medicine_id")
    @JoinColumn
    private Long medicineID;

    @Column(name = "quantity")
    @JsonProperty
    private Long quantity;

    @Column(name = "INSTRUCTION_NOTE")
    @JsonProperty
    private String instructionNote;
}
