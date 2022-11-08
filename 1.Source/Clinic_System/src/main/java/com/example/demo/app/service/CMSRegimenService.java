package com.example.demo.app.service;

import com.example.demo.app.entity.Regimen;
import com.example.demo.app.vo.MedicineDto;
import com.example.demo.app.vo.RegimenDto;
import com.example.demo.app.vo.RegimenReq;
import com.example.demo.app.vo.RegimenRes;

import java.util.List;

public interface CMSRegimenService {

    List<Regimen> getCriticalPathBySpecializedId(Long id);

    RegimenRes getRegimen(RegimenReq regimenReq);

    void addEditRegimen(RegimenReq regimenReq);

    RegimenDto getRegimenById(Long id);

    MedicineDto getRegimenMedicineById(Long id);

    List<Regimen> getRegimenAllPrescription();
}
