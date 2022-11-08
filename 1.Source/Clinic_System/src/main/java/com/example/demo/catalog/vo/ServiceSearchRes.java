package com.example.demo.catalog.vo;

import com.example.demo.app.entity.ServiceMaster;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Setter
@Getter
@ToString
public class ServiceSearchRes {
    private Long total;
    private List<ServiceMaster> listService;
}
