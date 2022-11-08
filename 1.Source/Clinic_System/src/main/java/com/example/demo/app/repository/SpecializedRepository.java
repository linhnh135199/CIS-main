//package com.example.demo.app.repository;
//
//import com.example.demo.app.entity.Specialized;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface SpecializedRepository extends CrudRepository<Specialized, Long> {
//
//    @Query("select m from Specialized m where m.name like %:key%")
//    Page<Specialized> getDataByName(@Param("key") String key, Pageable pageable);
//}
