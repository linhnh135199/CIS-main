package com.example.demo.user.service.impl;

import com.example.demo.user.entity.ConfirmationToken;
import com.example.demo.user.repository.ConfirmationTokenRepository;
import com.example.demo.user.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

	/** The confirmation token repository. */
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Override
	public void saveToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}

	@Override
	public ConfirmationToken findByToken(String token) {
		return confirmationTokenRepository.findByConfirmationToken(token);
	}

	@Override
	public void deleteTokenById(Long id) {
		confirmationTokenRepository.deleteById(id);
	}

	@Override
	public ConfirmationToken findByUserId(Long userId) {
		return confirmationTokenRepository.findByUserId(userId);
	}

}
