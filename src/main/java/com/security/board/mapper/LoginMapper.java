package com.security.board.mapper;

import com.security.board.model.Login;
import com.security.board.model.request.LoginRequest;
import com.security.board.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginMapper {

    private final PasswordEncoder encoder;

    public LoginResponse toLoginResponse(Login login) {
        return LoginResponse.builder()
                .id(login.getId())
                .email(login.getEmail())
                .creationDate(login.getCreationDate())
                .username(login.getUsername())
                .admin(login.getAdmin())
                .build();
    }

    public Login toLogin(LoginRequest loginRequest) {
        return Login.builder()
                .email(loginRequest.getEmail())
                .creationDate(loginRequest.getCreationDate())
                .senha(encoder.encode(loginRequest.getSenha()))
                .username(loginRequest.getUsername())
                .admin(loginRequest.getAdmin())
                .build();
    }
}
