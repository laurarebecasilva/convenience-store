package com.api.rest.user.registration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateDto(
        @NotBlank
        String username,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String password) {
    }


