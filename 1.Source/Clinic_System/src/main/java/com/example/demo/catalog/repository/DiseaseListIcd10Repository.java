package com.example.demo.catalog.repository;

import com.example.demo.app.entity.DiseaseListICD10;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseListIcd10Repository extends CrudRepository<DiseaseListICD10, Long> {
    @Query("select d from DiseaseListICD10 d where d.diseaseName like %:key%")
    Page<DiseaseListICD10> getAllByName(@Param("key") String key, Pageable pageable);

    DiseaseListICD10 findByDiseaseName(String diseaseName);
}
