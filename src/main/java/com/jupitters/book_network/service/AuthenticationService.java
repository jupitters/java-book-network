package com.jupitters.book_network.service;

import com.jupitters.book_network.dto.RegistrationRequest;

public interface AuthenticationService {
    void register(RegistrationRequest request);
}
