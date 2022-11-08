package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Constants.TABLE_EXAMINATION_ROOM)
@Getter
@Setter
public class ExaminationRoom extends BasicEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "roomCategoryId")
    @JsonProperty
    private Long roomCategoryId;

    @Column(name = "doctor_id", nullable = true)
    @JsonProperty
    private Long doctorId;

    @Column(name = "date", nullable = true)
    @JsonProperty
    private Date date;

    @Column(name = "hourAdmission", nullable = true)
    @JsonProperty
    private String hourAdmission;
}
