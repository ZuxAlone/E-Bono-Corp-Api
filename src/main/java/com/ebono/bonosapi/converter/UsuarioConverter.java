package com.ebono.bonosapi.converter;

import com.ebono.bonosapi.dto.LoginResponse;
import com.ebono.bonosapi.dto.SignupResponse;
import com.ebono.bonosapi.entities.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UsuarioConverter {

    public SignupResponse fromEntity(Usuario usuario) {
        if (usuario == null) return null;
        DateTimeFormatter formatObject = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = LocalDateTime.now().format(formatObject);
        return SignupResponse.builder()
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correo(usuario.getCorreo())
                .localDateTime(date)
                .build();
    }

    public LoginResponse fromEntity(Usuario usuario, String token) {
        if (usuario == null) return null;
        DateTimeFormatter formatObject = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = LocalDateTime.now().format(formatObject);
        return LoginResponse.builder()
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correo(usuario.getCorreo())
                .token(token)
                .localDateTime(date)
                .build();
    }
}
