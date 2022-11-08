package com.example.demo.catalog.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRes extends BaseRequest{
    private String name;
    private String specialized;
    private Long id;
    private Long specializedId;
    private String code;
    private String price;
    private Long soLuong;
}
