package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.repository.UserRepository;
import com.jupitters.book_network.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
}
