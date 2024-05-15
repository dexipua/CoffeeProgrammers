package com.school.config.JWT;

import com.school.dto.AuthResponse;
import com.school.dto.LoginRequest;
import com.school.models.Role;
import com.school.models.User;
import com.school.service.RoleService;
import com.school.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
            AuthResponse authResponse = new AuthResponse(user.getUsername(), jwtToken);
            return ResponseEntity.ok().body(authResponse);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            log.error("BadCredentialsException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }
    }

    @PostMapping("/auth/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid LoginRequest loginRequest) {
        Role role = roleService.findByName("USER");
        User user = userService.create(LoginRequest.convertToEntity(loginRequest, role));
        String jwtToken = jwtUtils.generateTokenFromUsername(user.getUsername());
        AuthResponse authResponse = new AuthResponse(user.getUsername(), jwtToken);
        return ResponseEntity.ok().body(authResponse);
    }
}

