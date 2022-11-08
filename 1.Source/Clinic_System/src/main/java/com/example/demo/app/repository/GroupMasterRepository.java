package com.example.demo.app.repository;

import com.example.demo.app.entity.GroupMaster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMasterRepository extends CrudRepository<GroupMaster, Long> {

    @Query("select m from GroupMaster m where m.groupName like %:key%")
    List<GroupMaster> getDataByName(@Param("key") String key);

}
