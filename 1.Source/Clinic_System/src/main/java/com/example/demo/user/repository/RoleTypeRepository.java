package com.example.demo.user.repository;

import com.example.demo.user.entity.RoleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleTypeRepository extends CrudRepository<RoleType, Long> {

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the role type
	 */
	RoleType findByName(String name);
}
