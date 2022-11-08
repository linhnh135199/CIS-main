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
//@Table(name = Constants.TABLE_SERVICE)
//@Getter
//@Setter
//public class Service extends BasicEntity{
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @Column(name = "name")
//    @JsonProperty
//    private String name;
//
//    @Column(name = "type")
//    @JsonProperty
//    private String type;
//
//    @Column(name = "price")
//    @JsonProperty
//    private String price;
//
//}
