package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = Constants.TABLE_REGIMEN_MEDICINE)
@Getter
@Setter
public class RegimenMedicine extends BasicEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty
    @Column(name = "regimenId")
    private Long regimenId;

    @JsonProperty
    @Column(name = "medicineId")
    private Long medicineId;
}
