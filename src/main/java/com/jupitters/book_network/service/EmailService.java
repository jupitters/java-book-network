package com.jupitters.book_network.service;

import com.jupitters.book_network.model.User;

public interface EmailService {
    void sendValidationEmail(User user);
}
