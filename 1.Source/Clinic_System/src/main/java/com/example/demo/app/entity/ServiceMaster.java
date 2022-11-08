package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity(name = "ServiceMaster")
@Table(name = Constants.TABLE_SERVICE_MASTER)
@Getter
@Setter
public class ServiceMaster extends BasicEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "code")
    private String code;

    @Column(name = "price")
    @JsonProperty
    private String price;

}
