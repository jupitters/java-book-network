package com.jupitters.book_network.service;

import com.jupitters.book_network.model.User;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendValidationEmail(User user) throws MessagingException;
}
