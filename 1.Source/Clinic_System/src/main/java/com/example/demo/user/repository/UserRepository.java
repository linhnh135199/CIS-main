package com.example.demo.user.repository;

import com.example.demo.user.entity.RoleType;
import com.example.demo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * find all user
	 * @return the list
	 */
	List<User> findAll();

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the user
	 */
	User findByEmail(String email);

	User findByPhoneNumber(String phone);

	@Query("select u from User u where u.roleId = :roleId and (u.name like %:key% or u.phoneNumber like %:key%)")
	Page<User> getAllByRoleIdAndKey(@Param("roleId") Long roleId, @Param("key") String key, Pageable pageable);

	@Query("select u from User u join Patient p on p.userID = u.id where u.name like %:key% or u.phoneNumber like %:key%")
	Page<User> getAllPatientIdAndKey(@Param("key") String key, Pageable pageable);

	@Query("select u from User u where u.roleId = :roleId")
	List<User> getAllDoctor(@Param("roleId") Long roleId);

	@Query("select u from User u where u.name like :name and u.phoneNumber like :phone")
	User getByPhoneNumberAndName(@Param("name") String name, @Param("phone") String phone);

}
