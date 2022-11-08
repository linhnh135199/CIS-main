//package com.example.demo.app.repository;
//
//import com.example.demo.app.entity.Medicine;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface MedicineRepository extends CrudRepository<Medicine, Long> {
//
//    @Query("select m from Medicine m where m.name like %:key%")
//    Page<Medicine> getDataByName(@Param("key") String key, Pageable pageable);
//}
