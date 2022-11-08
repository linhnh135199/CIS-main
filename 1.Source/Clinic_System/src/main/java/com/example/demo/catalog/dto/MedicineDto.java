package com.example.demo.catalog.dto;

import com.example.demo.app.entity.GenericMaster;
import com.example.demo.app.entity.MedicineMaster;
import com.example.demo.app.entity.RouteMaster;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MedicineDto {
    private Long id;
    private String name;
    private String unit;
    private String routeName;
    private String genericName;

    public MedicineDto() {

    }

    public MedicineDto(MedicineMaster m, GenericMaster g, RouteMaster r) {
        this.id = m.getId();
        this.name = m.getName();
        this.unit = m.getUnit();
        this.routeName = r.getName();
        this.genericName = g.getName();
    }
}
