package com.school.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @NotBlank
    private String username;
    @NotBlank
    private String accessToken;
}
