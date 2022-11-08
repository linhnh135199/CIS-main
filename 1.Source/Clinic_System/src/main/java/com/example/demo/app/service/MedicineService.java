package com.example.demo.app.service;

import com.example.demo.app.entity.MedicineMaster;
import com.example.demo.catalog.request.MedicineReq;

import java.util.List;

public interface MedicineService {
    List<MedicineMaster> getDataByName(String name);

    MedicineReq getDataById(Long id);
}
