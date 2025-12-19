package com.tradeconnect.config;

import com.tradeconnect.entity.Organization;
import com.tradeconnect.entity.Role;
import com.tradeconnect.entity.User;
import com.tradeconnect.repository.OrganizationRepository;
import com.tradeconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            if (userRepository.count() == 0) {
                Organization axis = Organization.builder()
                        .name("Axis Corporate")
                        .registrationNumber("ORG123")
                        .address("Mumbai, India")
                        .build();
                organizationRepository.save(axis);

                User maker = User.builder()
                        .username("maker")
                        .password(passwordEncoder.encode("password"))
                        .email("maker@axis.com")
                        .roles(Set.of(Role.ROLE_CORPORATE_MAKER))
                        .organization(axis)
                        .build();
                userRepository.save(maker);

                User checker = User.builder()
                        .username("checker")
                        .password(passwordEncoder.encode("password"))
                        .email("checker@axis.com")
                        .roles(Set.of(Role.ROLE_CORPORATE_CHECKER))
                        .organization(axis)
                        .build();
                userRepository.save(checker);

                User authorizer = User.builder()
                        .username("authorizer")
                        .password(passwordEncoder.encode("password"))
                        .email("auth@axis.com")
                        .roles(Set.of(Role.ROLE_CORPORATE_AUTHORIZER))
                        .organization(axis)
                        .build();
                userRepository.save(authorizer);

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("password"))
                        .email("admin@tfconnect.com")
                        .roles(Set.of(Role.ROLE_BANK_ADMIN))
                        .build();
                userRepository.save(admin);

                System.out.println(
                        "Test Users created: maker/password, checker/password, authorizer/password, admin/password");
            }
        };
    }
}
