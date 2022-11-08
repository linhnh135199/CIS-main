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
//@Table(name = Constants.TABLE_MEDICINE)
//@Getter
//@Setter
//public class Medicine extends BasicEntity{
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
//    @Column(name = "maHoatChat")
//    @JsonProperty
//    private String maHoatChat;
//
//    @Column(name = "hoat_chat")
//    @JsonProperty
//    private String hoatChat;
//
//    @Column(name = "ham_luong")
//    @JsonProperty
//    private String hamLuong;
//
//    @Column(name = "duong_dung")
//    @JsonProperty
//    private String duongDung;
//
//    @Column(name = "don_vi")
//    @JsonProperty
//    private String donVi;
//
//    @Column(name = "price")
//    @JsonProperty
//    private String price;
//
//    @Column(name = "so_luong")
//    @JsonProperty
//    private int soLuong;
//
//    @Column(name = "huong_dan")
//    @JsonProperty
//    private String huongDan;
//
//    @Column(name = "soDangKy")
//    @JsonProperty
//    private String soDangKy;
//
//    @Column(name = "dongGoi")
//    @JsonProperty
//    private String dongGoi;
//
//    @Column(name = "hangSX")
//    @JsonProperty
//    private String hangSX;
//
//    @Column(name = "nuocSX")
//    @JsonProperty
//    private String nuocSX;
//
//}
