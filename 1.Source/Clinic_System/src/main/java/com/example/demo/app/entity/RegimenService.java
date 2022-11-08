package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = Constants.TABLE_REGIMEN_SERVICE)
@Getter
@Setter
public class RegimenService extends BasicEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty
    @Column(name = "regimenId")
    private Long regimenId;

    @JsonProperty
    @Column(name = "serviceId")
    private Long serviceId;
}
