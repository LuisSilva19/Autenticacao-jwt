package com.security.board.controller;

import com.security.board.model.request.LoginRequest;
import com.security.board.model.response.LoginResponse;
import com.security.board.service.LoginService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/credit/login")
@AllArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> createLogin(@Valid @RequestBody LoginRequest login) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loginService.createLogin(login));
    }

}
