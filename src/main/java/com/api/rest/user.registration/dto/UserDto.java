package com.api.rest.user.registration.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDto(
        @Id
        Long id,

        @NotBlank
        String username,

        @NotBlank
        @Pattern(regexp = "\\d{6,8}", message = "The password must contain between 6 and 8 digits") //Expressão regular
        String password,

        @NotBlank
        @Email
        String email)
{
}
