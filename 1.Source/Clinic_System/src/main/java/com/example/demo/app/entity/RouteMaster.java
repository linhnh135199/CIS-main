package com.example.demo.app.entity;

import lombok.Data;
import javax.persistence.*;


@Entity(name = "RouteMaster")
@Table(name = "route_master")
@Data
public class RouteMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

}
