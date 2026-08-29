package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.model.User;
import com.jupitters.book_network.service.EmailService;
import com.jupitters.book_network.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final TokenService tokenService;

    @Override
    public void sendValidationEmail(User user) {
        String newToken = tokenService.generateAndSaveActivationToken(user);
    }
}
