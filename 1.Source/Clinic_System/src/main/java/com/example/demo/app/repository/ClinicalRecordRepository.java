package com.example.demo.app.repository;

import com.example.demo.app.entity.ClinicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicalRecordRepository extends CrudRepository<ClinicalRecord, Long> {
}
