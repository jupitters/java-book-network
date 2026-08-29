package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.model.User;
import com.jupitters.book_network.service.EmailService;
import com.jupitters.book_network.service.TokenService;
import com.jupitters.book_network.template.EmailTemplateName;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final TokenService tokenService;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendValidationEmail(User user) {
        String newToken = tokenService.generateAndSaveActivationToken(user);
    }

    public void sendEmail(String to, String username, EmailTemplateName emailTemplate, String confirmationUrl, String confirmationCode, String subject){

    }
}
