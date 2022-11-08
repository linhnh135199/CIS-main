package com.example.demo.user.repository;

import com.example.demo.user.entity.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

	/**
	 * Find by confirmation token.
	 *
	 * @param token the token
	 * @return the confirmation token
	 */
	ConfirmationToken findByConfirmationToken(String token);

	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the confirmation token
	 */
	ConfirmationToken findByUserId(Long userId);
}
