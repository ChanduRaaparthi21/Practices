package com.chandu.s_100_exchange_set_builder.model;

import jakarta.validation.constraints.NotBlank;

public class ExchangeSetDto {

    @NotBlank(message = "Name is required")
    private String name;

    private String authenticationKey; // Optional for creation, maybe required for some operations

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthenticationKey() {
        return authenticationKey;
    }

    public void setAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }
}
