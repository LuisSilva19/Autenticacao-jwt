package com.security.board.service;

import com.security.board.configuration.security.JwtService;
import com.security.board.exception.CustomException;
import com.security.board.model.Login;
import com.security.board.model.request.LoginAuthRequest;
import com.security.board.repository.LoginRepository;
import com.security.board.service.security.AuthService;
import java.util.Optional;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;
    @Mock
    LoginRepository loginRepository;
    @Mock
    JwtService jwtService;
    @Mock
    PasswordEncoder encoder;

    @Test
    void should_validade_login(){
        Mockito.when(loginRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new EasyRandom().nextObject(Login.class)));
        Mockito.when(encoder.matches(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> authService.validLogin(new EasyRandom().nextObject(LoginAuthRequest.class)));
    }

    @Test
    void not_should_validade_login(){
        Mockito.when(loginRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new EasyRandom().nextObject(Login.class)));

        Assertions.assertThrows(CustomException.class,() -> authService.validLogin(new EasyRandom().nextObject(LoginAuthRequest.class)));
    }

}
