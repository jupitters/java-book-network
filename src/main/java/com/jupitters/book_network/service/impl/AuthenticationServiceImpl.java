package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.dto.RegistrationRequest;
import com.jupitters.book_network.model.User;
import com.jupitters.book_network.repository.RoleRepository;
import com.jupitters.book_network.repository.UserRepository;
import com.jupitters.book_network.roles.Role;
import com.jupitters.book_network.service.AuthenticationService;
import com.jupitters.book_network.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public void register(RegistrationRequest request) {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER was not initialized!"));
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        emailService.sendValidationEmail(user);
    }
}
