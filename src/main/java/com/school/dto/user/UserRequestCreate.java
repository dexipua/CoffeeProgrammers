package com.school.dto.user;

import com.school.models.User;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserRequestCreate {

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

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    public static User toUser(UserRequestCreate userRequestCreate) { //TODO maybe constructor
        return new User(
                userRequestCreate.firstName,
                userRequestCreate.lastName,
                userRequestCreate.getEmail(),
                userRequestCreate.password
        );

    }
}
