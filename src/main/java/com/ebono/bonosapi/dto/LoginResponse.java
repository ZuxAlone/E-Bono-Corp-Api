package com.ebono.bonosapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String nombres;
    private String apellidos;
    private String correo;
    private String token;
    private LocalTime localTime;
}
