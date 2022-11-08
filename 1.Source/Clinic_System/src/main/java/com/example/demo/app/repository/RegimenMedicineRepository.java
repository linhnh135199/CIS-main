package com.example.demo.app.repository;

import com.example.demo.app.entity.RegimenMedicine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegimenMedicineRepository extends CrudRepository<RegimenMedicine, Long> {

    RegimenMedicine findAllByRegimenIdAndMedicineId(Long regimenId, Long medicineId);

    List<RegimenMedicine> findAllByRegimenId(Long medicineId);


}
