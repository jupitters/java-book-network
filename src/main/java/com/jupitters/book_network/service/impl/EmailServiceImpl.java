package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.model.User;
import com.jupitters.book_network.service.EmailService;
import com.jupitters.book_network.service.TokenService;
import com.jupitters.book_network.template.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final TokenService tokenService;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public void sendValidationEmail(User user) throws MessagingException {
        String newToken = tokenService.generateAndSaveActivationToken(user);
        sendEmail(user.getEmail(), user.getFullName(), EmailTemplateName.ACTIVATE_ACCOUNT, activationUrl, newToken, "Account Activation");
    }

    @Async
    public void sendEmail(String to, String username, EmailTemplateName emailTemplate, String confirmationUrl, String confirmationCode, String subject) throws MessagingException {
        String templateName;
        if(emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", confirmationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("contact@jupitters.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);
    }
}
