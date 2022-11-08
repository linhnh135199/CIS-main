package com.example.demo.user.service;

import com.example.demo.user.entity.ConfirmationToken;


public interface ConfirmationTokenService {

	/**
	 * Save token.
	 *
	 * @param token the token
	 */
	void saveToken(ConfirmationToken token);

	/**
	 * Find by token.
	 *
	 * @param token the token
	 * @return the confirmation token
	 */
	ConfirmationToken findByToken(String token);

	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the confirmation token
	 */
	ConfirmationToken findByUserId(Long userId);

	/**
	 * Delete token by id.
	 *
	 * @param id the id
	 */
	void deleteTokenById(Long id);
}
