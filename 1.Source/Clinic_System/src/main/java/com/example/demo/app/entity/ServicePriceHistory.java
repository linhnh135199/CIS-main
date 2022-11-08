package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@javax.persistence.Entity
@Table(name = Constants.TABLE_SERVICE_PRICE_HISTORY)
@Getter
@Setter
public class ServicePriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sph_id", nullable = false)
    @JsonProperty
    private Long sphID;

    @Column(name = "service_id")
    @JoinColumn
    private Long serviceID;

    @Column(name = "from_date")
    @JsonProperty
    private Date fromDate;

    @Column(name = "to_date")
    @JsonProperty
    private Date toDate;

    @Column(name = "price")
    @JsonProperty
    private String price;
}
