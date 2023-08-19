package com.security.board.controller.security;

import com.security.board.configuration.security.JwtService;
import com.security.board.model.AuthResponse;
import com.security.board.model.request.LoginAuthRequest;
import com.security.board.service.security.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginAuthRequest loginAuthRequest) {
        return ResponseEntity.ok(authService.validLogin(loginAuthRequest));
    }

    @PostMapping("/refresh")
    @SecurityRequirement(name = "javainuseapi")
    public ResponseEntity<AuthResponse> refresh(@RequestBody String token) {
        return ResponseEntity.ok(jwtService.refreshToken(token));
    }
}