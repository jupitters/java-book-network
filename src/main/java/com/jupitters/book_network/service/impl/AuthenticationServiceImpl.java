package com.jupitters.book_network.service.impl;

import com.jupitters.book_network.dto.AuthenticationRequest;
import com.jupitters.book_network.dto.AuthenticationResponse;
import com.jupitters.book_network.dto.RegistrationRequest;
import com.jupitters.book_network.model.Token;
import com.jupitters.book_network.model.User;
import com.jupitters.book_network.repository.RoleRepository;
import com.jupitters.book_network.repository.TokenRepository;
import com.jupitters.book_network.repository.UserRepository;
import com.jupitters.book_network.roles.Role;
import com.jupitters.book_network.security.jwt.JwtService;
import com.jupitters.book_network.service.AuthenticationService;
import com.jupitters.book_network.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public void register(RegistrationRequest request) throws MessagingException {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER was not initialized!"));
        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        emailService.sendValidationEmail(user);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var authManager = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = (User) authManager.getPrincipal();
        claims.put("fullName", user.getFullName());
        String jwtToken = jwtService.generateToken(claims, user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalid"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            emailService.sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address.");
        }

        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new RuntimeException(("User not found")));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
