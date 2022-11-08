package com.example.demo.app.repository;

import com.example.demo.app.entity.ServiceOfPatient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOfPatientRepository extends CrudRepository<ServiceOfPatient, Long> {

    List<ServiceOfPatient> findAllByPatientId(Long id);

}
