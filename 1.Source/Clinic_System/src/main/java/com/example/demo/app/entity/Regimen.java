package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = Constants.TABLE_REGIMEN)
@Getter
@Setter
public class Regimen extends BasicEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @JsonProperty
    private String name;

    @JsonProperty
    @Column(name = "status")
    private String status;

    @JsonProperty
    @Column(name = "type")
    private String type;

    @JsonProperty
    @Column(name = "createUserId")
    private Long createUserId;

}
