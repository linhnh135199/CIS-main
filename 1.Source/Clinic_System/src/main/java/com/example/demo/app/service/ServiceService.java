package com.example.demo.app.service;

import com.example.demo.app.entity.ServiceMaster;
import com.example.demo.app.vo.PatientServiceReq;
import com.example.demo.catalog.request.ServiceRes;

import java.util.List;

public interface ServiceService {

    List<ServiceRes> getServiceByPatientId(Long id);

    List<ServiceRes> getServiceBySpecializedId(Long id);

    List<ServiceRes> getServiceByCriticalPathId(String ids);

    void updateServiceToPatient(PatientServiceReq patientServiceReq);

    List<ServiceMaster> getDataByNameAndSpecialized(String key, Long specializedId);

}
