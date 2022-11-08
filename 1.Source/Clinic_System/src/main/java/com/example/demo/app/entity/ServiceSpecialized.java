//package com.example.demo.app.entity;
//
//import com.example.demo.common.Constants;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//
//@Entity
//@Table(name = Constants.TABLE_SERVICE_SPECIALIZED)
//@Getter
//@Setter
//public class ServiceSpecialized extends BasicEntity{
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @Column(name = "serviceId")
//    @JsonProperty
//    private Long serviceId;
//
//    @Column(name = "specializedId")
//    @JsonProperty
//    private Long specializedId;
//}
