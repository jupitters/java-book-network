package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.model.User;
import com.jupitters.book_network.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Override
    public String generateAndSaveGenerationToken(User user) {
        String generatedToken = generateActivationCode(6);
        return "";
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
