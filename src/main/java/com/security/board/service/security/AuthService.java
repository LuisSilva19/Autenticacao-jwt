package com.security.board.service.security;

import com.security.board.configuration.security.JwtService;
import com.security.board.exception.CustomException;
import com.security.board.exception.ExceptionCodes;
import com.security.board.model.AuthResponse;
import com.security.board.model.request.LoginAuthRequest;
import com.security.board.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final LoginRepository loginRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthResponse validLogin(LoginAuthRequest loginAuthRequest) {
        var loginOptional = loginRepository.findByEmail(loginAuthRequest.getEmail());
        if (loginOptional.isEmpty()) {
            throw new CustomException(ExceptionCodes.EMAIL_OR_SENHA_INVALID);
        }

        var login = loginOptional.get();

        if (!encoder.matches(loginAuthRequest.getSenha(), login.getSenha())) {
            throw new CustomException(ExceptionCodes.EMAIL_OR_SENHA_INVALID);
        }

        loginAuthRequest.setAdmin(login.getAdmin());

        var token = jwtService.generateToken(loginAuthRequest);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}