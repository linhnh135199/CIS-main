package com.example.demo.app.repository;

import com.example.demo.app.entity.PatientDiagnosis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientDiagnosisRepository extends CrudRepository<PatientDiagnosis, Long> {

    List<PatientDiagnosis> findByPatientId(Long id);

    PatientDiagnosis findByPatientIdAndDiseaseListICD10Id(Long patientId,Long diseaseListICD10Id);

}
