package com.ebono.bonosapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String nombres;
    private String apellidos;
    private String correo;
    private String token;
    private String localDateTime;
}
