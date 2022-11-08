package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = Constants.TABLE_ADMISSION_RECORD)
@Getter
@Setter
public class AdmissionRecord extends BasicEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "patient_id")
    @JsonProperty
    private Long patientID;

    @Column(name = "doctor_id", nullable = true)
    @JsonProperty
    private Long doctorId;

    @Column(name = "priority")
    @JsonProperty
    private Boolean priority;

    @Column(name = "status")
    @JsonProperty
    private String status;

    @Column(name = "statusApprove")
    @JsonProperty
    private String statusApprove;

    @Column(name = "examination_reason")
    @JsonProperty
    private String examinationReason;

    @Column(name = "examination_service_id")
    @JsonProperty
    private Long examinationServiceID;

    @Column(name = "examination_room_id")
    @JsonProperty
    private Long examinationRoomID;

    @Column(name = "examination_order")
    @JsonProperty
    private String examinationOrder;

    @Column(name = "admission_date")
    @JsonProperty
    private Date admissionDate;

    @Column(name = "admission_hour")
    @JsonProperty
    private String admissionHour;
}
