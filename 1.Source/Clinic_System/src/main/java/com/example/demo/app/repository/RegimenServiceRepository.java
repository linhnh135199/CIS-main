package com.example.demo.app.repository;

import com.example.demo.app.entity.RegimenService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegimenServiceRepository extends CrudRepository<RegimenService, Long> {

    List<RegimenService> findAllByRegimenId(Long id);

    RegimenService findAllByRegimenIdAndServiceId(Long regimenId, Long serviceId);

}
