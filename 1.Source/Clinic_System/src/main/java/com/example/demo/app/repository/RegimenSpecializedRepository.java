package com.example.demo.app.repository;

import com.example.demo.app.entity.RegimenSpecialized;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegimenSpecializedRepository extends CrudRepository<RegimenSpecialized, Long> {

    List<RegimenSpecialized> findAllBySpecializedId(Long id);

    List<RegimenSpecialized> findAllByRegimenId(Long id);

    RegimenSpecialized findAllByRegimenIdAndSpecializedId(Long regimenId, Long specializedId);

}
