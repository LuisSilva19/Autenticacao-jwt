package com.security.board.service;

import com.security.board.mapper.LoginMapper;
import com.security.board.model.Login;
import com.security.board.model.request.LoginRequest;
import com.security.board.repository.LoginRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    LoginService loginService;
    @Mock
    LoginRepository loginRepository;
    @Mock
    LoginMapper loginMapper;

    @Test
    void should_load_user_by_username(){
        Login login = new EasyRandom().nextObject(Login.class);
        login.setUsername("user");
        Mockito.when(loginRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(login));

        List<UserDetails> response = new ArrayList<>();
        Assertions.assertDoesNotThrow(() -> response.add(loginService.loadUserByUsername("test@email.com")));

        Assertions.assertNotNull(response.get(0));
    }

    @Test
    void not_should_load_user_by_username(){
        Mockito.when(loginRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> loginService.loadUserByUsername("test@email.com"));

    }

    @Test
    void should_create_login(){
        Assertions.assertDoesNotThrow(() -> loginService.createLogin(new EasyRandom().nextObject(LoginRequest.class)));
    }

}
