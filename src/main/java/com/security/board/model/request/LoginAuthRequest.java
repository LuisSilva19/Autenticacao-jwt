package com.security.board.model.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class LoginAuthRequest{
    @Email
    @NotBlank
    private String email;
    @NotNull
    private String senha;
    @JsonIgnore
    private Boolean admin;
}