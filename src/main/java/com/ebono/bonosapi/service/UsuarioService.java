package com.ebono.bonosapi.service;

import com.ebono.bonosapi.dto.UsuarioRequest;
import com.ebono.bonosapi.entities.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Usuario getUsuarioInfo(UsuarioRequest usuarioRequest) {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombres(usuarioRequest.getNombres())
                .apellidos(usuarioRequest.getApellidos())
                .correo(usuarioRequest.getCorreo())
                .password(usuarioRequest.getPassword())
                .num_bonos_simulados(0)
                .build();

        return usuario;
    }
}
