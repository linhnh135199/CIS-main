package com.example.demo.app.repository;

import com.example.demo.app.entity.RoomCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomCategoryRepository extends CrudRepository<RoomCategory, Long> {

}
