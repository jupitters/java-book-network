package com.jupitters.book_network.service;

import com.jupitters.book_network.dto.AuthenticationRequest;
import com.jupitters.book_network.dto.AuthenticationResponse;
import com.jupitters.book_network.dto.RegistrationRequest;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

public interface AuthenticationService {
    void register(RegistrationRequest request) throws MessagingException;

    AuthenticationResponse authenticate(@Valid AuthenticationRequest request);
}
