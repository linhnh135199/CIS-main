package com.example.demo.app.entity;

import com.example.demo.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;


@Entity(name = "MedicineMaster")
@Table(name = Constants.TABLE_MEDICINE_MASTER)
@Data
public class MedicineMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit")
    private String unit;

    @Column(name = "generic_master_code")
    private String genericMasterCode;

    @Column(name = "soDangKy")
    @JsonProperty
    private String soDangKy;

    @Column(name = "dongGoi")
    @JsonProperty
    private String dongGoi;

    @Column(name = "hangSX")
    @JsonProperty
    private String hangSX;

    @Column(name = "nuocSX")
    @JsonProperty
    private String nuocSX;
}
