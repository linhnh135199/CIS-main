package com.example.demo.app.repository;

import com.example.demo.app.entity.ServicePriceHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicePriceHistoryRepository extends CrudRepository<ServicePriceHistory, Long> {

    List<ServicePriceHistory> findByServiceID(Long id);
}
