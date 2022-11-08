package com.example.demo.app.repository;

import com.example.demo.app.entity.RouteMaster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteMasterRepository extends CrudRepository<RouteMaster, Long> {

    @Query("select m from RouteMaster m where m.name like %:key%")
    List<RouteMaster> getDataByName(@Param("key") String key);

    RouteMaster findByCode(String maDuongDung);
}
