package com.example.demo.masterdata.repository;

import com.example.demo.masterdata.entity.MasterData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterDataRepository extends CrudRepository<MasterData, Long> {

	/**
	 * Find by name and category.
	 *
	 * @param name     the name
	 * @param category the category
	 * @return the master data
	 */
	@Query("Select m from MasterData m WHERE m.name = :name AND (:category IS NULL OR m.category = :category)")
	MasterData findByNameAndCategory(@Param("name") String name, @Param("category") String category);
	
	/**
   * Find by name 
   * 
   * @param name     the name
   * @param category the category
   * @return the master data
   */
  @Query("Select m from MasterData m WHERE m.name = :name and m.id <> :id ")
  MasterData checkNameExists(@Param("name") String name, @Param("id") Long id);
  
}
