//package com.example.demo.catalog.repository;
//
//
//import com.example.demo.app.entity.Service;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ServiceRepository extends JpaRepository<Service, Long> {
//    @Query("select s from Service s join ServiceSpecialized ss on ss.serviceId = s.id " +
//            "where s.name like %:key% and ss.specializedId = :specializedId")
//    Page<Service> getDataByNameAndSpecialized(@Param("key") String key, @Param("specializedId") Long specializedId, Pageable pageable);
//
//    @Query("select s from Service s where s.name like %:key% ")
//    Page<Service> getDataByName(@Param("key") String key, Pageable pageable);
//}
