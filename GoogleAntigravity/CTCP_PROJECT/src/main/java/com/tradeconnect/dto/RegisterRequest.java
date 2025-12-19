package com.tradeconnect.dto;

import com.tradeconnect.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Long organizationId;
    private Set<Role> roles;

    // Optional fields for creating a new organization
    private String organizationName;
    private String organizationRegNumber;
    private String organizationAddress;
}
