package com.example.demo.app.service;

import com.example.demo.app.entity.MedicineMaster;
import com.example.demo.app.vo.PatientPrescriptionReq;
import com.example.demo.catalog.request.MedicineReq;

import java.util.List;

public interface PrescriptionOfPatientService {

    List<MedicineReq> getPrescriptionOfPatientById(Long id);

    void updatePrescriptionToPatient(PatientPrescriptionReq patientPrescriptionReq);

    List<MedicineReq> getPrescriptionByRegimen(Long id);
}
