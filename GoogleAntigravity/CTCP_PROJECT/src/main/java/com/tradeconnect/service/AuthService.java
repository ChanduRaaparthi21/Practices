package com.tradeconnect.service;

import com.tradeconnect.dto.AuthRequest;
import com.tradeconnect.dto.AuthResponse;
import com.tradeconnect.dto.RegisterRequest;
import com.tradeconnect.entity.Organization;
import com.tradeconnect.entity.User;
import com.tradeconnect.repository.OrganizationRepository;
import com.tradeconnect.repository.UserRepository;
import com.tradeconnect.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository userRepository;
        private final OrganizationRepository organizationRepository;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;

        public AuthResponse register(RegisterRequest request) {
                Organization organization = null;

                // 1. Check if user wants to create a new organization
                if (request.getOrganizationName() != null && !request.getOrganizationName().isEmpty()) {
                        organization = Organization.builder()
                                        .name(request.getOrganizationName())
                                        .registrationNumber(request.getOrganizationRegNumber())
                                        .address(request.getOrganizationAddress())
                                        .active(true)
                                        .build();
                        organization = organizationRepository.save(organization);
                }
                // 2. Otherwise use existing ID if provided
                else if (request.getOrganizationId() != null) {
                        organization = organizationRepository.findById(request.getOrganizationId())
                                        .orElseThrow(() -> new RuntimeException("Organization not found"));
                }

                User user = User.builder()
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .email(request.getEmail())
                                .roles(request.getRoles())
                                .organization(organization)
                                .enabled(true)
                                .build();

                userRepository.save(user);

                String jwtToken = jwtService.generateToken(user);
                return AuthResponse.builder()
                                .token(jwtToken)
                                .username(user.getUsername())
                                .roles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                                .organizationName(organization != null ? organization.getName() : "BANK")
                                .build();
        }

        public AuthResponse authenticate(AuthRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));
                User user = userRepository.findByUsername(request.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                String jwtToken = jwtService.generateToken(user);

                return AuthResponse.builder()
                                .token(jwtToken)
                                .username(user.getUsername())
                                .roles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                                .organizationName(user.getOrganization() != null ? user.getOrganization().getName()
                                                : "BANK")
                                .build();
        }
}
