package com.example.demo.app.repository;

import com.example.demo.app.entity.MedicineRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRouteRepository extends CrudRepository<MedicineRoute, Long> {

    MedicineRoute findByRouteMasterId(Long routeMasterId);

    MedicineRoute findByMedicineMasterId(Long medicineMasterId);
}
