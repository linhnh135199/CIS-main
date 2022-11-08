package com.example.demo.app.repository;

import com.example.demo.app.entity.Regimen;
import com.example.demo.app.entity.RegimenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegimenRepository extends CrudRepository<Regimen, Long> {

    @Query("select r from Regimen r join RegimenSpecialized rs on r.id = rs.regimenId " +
            "join GroupMaster s on s.id = rs.specializedId where r.type = :type and s.id = :specializedId " +
            "and r.name like %:name% and r.status = :status and (r.status = 'Công khai' or (r.status = 'Riêng Tư' and r.createUserId = :createUserId))")
    Page<Regimen> getAllDataByDataSearch(@Param("type") String type, @Param("specializedId") Long specializedId, @Param("name") String name,
                                         @Param("status") String status, @Param("createUserId") Long createUserId, Pageable pageable);

    @Query("select r from Regimen r join RegimenSpecialized rs on r.id = rs.regimenId " +
            "join GroupMaster s on s.id = rs.specializedId where r.type = :type " +
            "and r.name like %:name% and r.status = :status and (r.status = 'Công khai' or (r.status = 'Riêng Tư' and r.createUserId = :createUserId))")
    Page<Regimen> getAllDataByDataSearchAllSpecialized(@Param("type") String type, @Param("name") String name,
                                         @Param("status") String status, @Param("createUserId") Long createUserId, Pageable pageable);

    @Query("select r from Regimen r where r.type = 'Thuốc' and (r.status = 'Công khai' or (r.status = 'Riêng Tư' and r.createUserId = :createUserId))")
    List<Regimen> getRegimenAllPrescription(@Param("createUserId") Long createUserId);

}
