package com.school.config.JWT;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final List<Class<? extends AuthenticationException>> IGNORED_EXCEPTIONS = List.of(

    );

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (shouldLogException(authException)) {
            log.error("AuthenticationException: {}", authException.getMessage());
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }

    private boolean shouldLogException(AuthenticationException exception) {
        return IGNORED_EXCEPTIONS.stream().noneMatch(ignoredException -> ignoredException.isInstance(exception));
    }
}

