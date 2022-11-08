package com.example.demo.app.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "MedicineRoute")
@Table(name = "medicine_route")
@Data
public class MedicineRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_master_id")
    private Long routeMasterId;

    @Column(name = "medicine_master_id")
    private Long medicineMasterId;
}
