package com.example.demo.app.repository;

import com.example.demo.app.entity.PrescriptionOfPatient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionOfPatientRepository extends CrudRepository<PrescriptionOfPatient, Long> {

    List<PrescriptionOfPatient> findAllByPatientId(Long id);

}
