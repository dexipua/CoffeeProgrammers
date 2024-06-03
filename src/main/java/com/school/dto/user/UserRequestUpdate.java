package com.school.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserRequestUpdate {
    @Pattern(regexp = "[A-Z][a-z]+",
            message = "First name must start with a capital letter followed by one or more lowercase letters")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Last name must start with a capital letter followed by one or more lowercase letters")
    private String lastName;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$",
            message = "Password must be minimum 6 symbols long, using digits and latin letters")
    @Pattern(regexp = ".*\\d.*",
            message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[A-Z].*",
            message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*",
            message = "Password must contain at least one lowercase letter")
    private String password;
}
