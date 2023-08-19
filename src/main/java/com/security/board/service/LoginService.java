package com.security.board.service;

import com.security.board.mapper.LoginMapper;
import com.security.board.model.request.LoginRequest;
import com.security.board.model.response.LoginResponse;
import com.security.board.repository.LoginRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService implements UserDetailsService {
    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var login = loginRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = Boolean.TRUE.equals(login.getAdmin()) ?
                new String[]{"ADMIN"} : new String[]{};

        return User
                .builder()
                .username(login.getEmail())
                .password(login.getSenha())
                .roles(roles)
                .build();
    }

    public LoginResponse createLogin(LoginRequest loginResponse) {
        var login = loginMapper.toLogin(loginResponse);
        var loginSave = loginRepository.save(login);
        return loginMapper.toLoginResponse(loginSave);
    }

}
