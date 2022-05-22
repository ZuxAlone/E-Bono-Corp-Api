package com.ebono.bonosapi.controller;

import com.ebono.bonosapi.converter.UsuarioConverter;
import com.ebono.bonosapi.dto.SignupRequest;
import com.ebono.bonosapi.dto.SignupResponse;
import com.ebono.bonosapi.entities.Usuario;
import com.ebono.bonosapi.service.UsuarioService;
import com.ebono.bonosapi.utils.WrapperResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/signup")
public class SingupController {

    private final UsuarioService usuarioService;
    private final UsuarioConverter usuarioConverter;

    public SingupController(UsuarioService usuarioService, UsuarioConverter usuarioConverter) {
        this.usuarioService = usuarioService;
        this.usuarioConverter = usuarioConverter;
    }

    @PostMapping
    public ResponseEntity<WrapperResponse<SignupResponse>> signup(@RequestBody SignupRequest signupRequest) {
        Usuario usuario = usuarioService.signupUsuario(signupRequest);
        return new WrapperResponse<>(true, "New user created", usuarioConverter.fromEntity(usuario))
                .createResponse();
    }
}
