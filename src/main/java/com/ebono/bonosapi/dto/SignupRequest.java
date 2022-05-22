package com.ebono.bonosapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class SignupRequest {
    @NotBlank
    @NotNull
    private String nombres;
    @NotBlank
    @NotNull
    private String apellidos;
    @NotBlank
    @NotNull
    private String correo;
    @NotBlank
    @NotNull
    private String password;
}
