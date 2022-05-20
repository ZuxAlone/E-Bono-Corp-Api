package com.ebono.bonosapi.controller;

import com.ebono.bonosapi.common.EntityDtoConverter;
import com.ebono.bonosapi.dto.UsuarioRequest;
import com.ebono.bonosapi.dto.UsuarioResponse;
import com.ebono.bonosapi.entities.Usuario;
import com.ebono.bonosapi.service.UsuarioService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EntityDtoConverter entityDtoConverter;

    public UsuarioController(UsuarioService usuarioService, EntityDtoConverter entityDtoConverter) {
        this.usuarioService = usuarioService;
        this.entityDtoConverter = entityDtoConverter;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> getUsuarioInfo(@RequestBody UsuarioRequest usuarioRequest) throws Exception {
        Usuario usuario = usuarioService.getUsuarioInfo(usuarioRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoUser(usuario),
                                    HttpStatus.OK);
    }
}
