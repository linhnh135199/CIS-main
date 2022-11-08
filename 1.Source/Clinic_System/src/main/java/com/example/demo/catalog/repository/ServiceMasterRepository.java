package com.example.demo.catalog.repository;

import com.example.demo.app.entity.ServiceMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceMasterRepository extends JpaRepository<ServiceMaster, Long> {

    @Query("select m from ServiceMaster m where m.serviceName like %:key%")
    Page<ServiceMaster> getDataByName(@Param("key") String key, Pageable pageable);

    @Query("select s from ServiceMaster s " +
            "where s.serviceName like %:key% and s.groupId = :specializedId")
    Page<ServiceMaster> getDataByNameAndSpecialized(@Param("key") String key, @Param("specializedId") Long specializedId, Pageable pageable);

    List<ServiceMaster> findByGroupId(Long id);
}
