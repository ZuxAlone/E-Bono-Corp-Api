package com.ebono.bonosapi.converter;

import com.ebono.bonosapi.dto.LoginResponse;
import com.ebono.bonosapi.dto.SignupResponse;
import com.ebono.bonosapi.entities.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class UsuarioConverter {

    public SignupResponse fromEntity(Usuario usuario) {
        if (usuario == null) return null;
        return SignupResponse.builder()
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correo(usuario.getCorreo())
                .localTime(LocalTime.now())
                .build();
    }

    public LoginResponse fromEntity(Usuario usuario, String token) {
        if (usuario == null) return null;
        return LoginResponse.builder()
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correo(usuario.getCorreo())
                .token(token)
                .localTime(LocalTime.now())
                .build();
    }
}
