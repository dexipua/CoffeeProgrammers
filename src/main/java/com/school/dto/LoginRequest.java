package com.school.dto;

import com.school.models.Role;
import com.school.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public static User convertToEntity(LoginRequest loginRequest, Role role) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setFirstName("New");
        user.setLastName("New");
        user.setEmail("New@email.com");
        user.setPassword(loginRequest.getPassword());
        user.setRole(role);
        return user;
    }
}
