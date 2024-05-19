package com.school.dto;

import com.school.models.Role;
import com.school.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
