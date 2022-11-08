package com.example.demo.app.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Table(name = "generic_master")
@Entity(name = "GenericMaster")
@Getter
@Setter
public class GenericMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
}
