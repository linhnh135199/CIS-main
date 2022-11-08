package com.example.demo.catalog.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineRequest extends BaseRequest{
    private String name;
    private String unit;
}
