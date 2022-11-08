package com.example.demo.catalog.service;

import com.example.demo.catalog.dto.MedicineDto;
import com.example.demo.catalog.request.MedicineReq;
import com.example.demo.catalog.request.MedicineRequest;
import com.example.demo.catalog.response.PageResponse;
import com.example.demo.catalog.vo.Icd10SearchReq;

import java.util.List;

public interface MedicineService {
    List<MedicineDto> getAll();

    boolean deleteMedicineById(long id);

    PageResponse getData(Icd10SearchReq icd10SearchReq);

    void addEditMedicine(MedicineReq medicineReq);

    MedicineReq getDataMedicineById(Long id);
}
