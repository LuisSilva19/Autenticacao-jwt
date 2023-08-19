package com.security.board.model.request;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest{
    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotNull
    private LocalDate creationDate;
    @NotBlank
    private String username;
    @NotNull
    private Boolean admin;
}
