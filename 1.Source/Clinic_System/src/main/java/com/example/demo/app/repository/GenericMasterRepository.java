package com.example.demo.app.repository;

import com.example.demo.app.entity.GenericMaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenericMasterRepository extends CrudRepository<GenericMaster, Long> {

    List<GenericMaster> findByCode(String maDuongDung);
}
