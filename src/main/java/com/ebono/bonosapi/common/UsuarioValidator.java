package com.ebono.bonosapi.common;

import com.ebono.bonosapi.dto.SignupRequest;
import com.ebono.bonosapi.exception.BadResourceRequestException;

public class UsuarioValidator {
    public static boolean validateUser(SignupRequest usuario) {
        if(usuario.getNombres().isEmpty() || usuario.getApellidos().isEmpty() || usuario.getCorreo().isEmpty()
                || usuario.getPassword().isEmpty()) {
            throw new BadResourceRequestException("Completar todos los datos en blanco.");
        }
        return true;
    }
}
