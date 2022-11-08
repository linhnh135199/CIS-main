package com.example.demo.catalog.repository;

import com.example.demo.app.entity.MedicineMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineMasterRepository extends JpaRepository<MedicineMaster, Long > {

    @Query("select m from MedicineMaster m where m.name like %:key%")
    Page<MedicineMaster> getDataByName(@Param("key") String key, Pageable pageable);

    List<MedicineMaster> findAllByGenericMasterCode(String code);
}
