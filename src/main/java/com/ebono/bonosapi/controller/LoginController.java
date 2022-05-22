package com.ebono.bonosapi.controller;

import com.ebono.bonosapi.dto.LoginRequest;
import com.ebono.bonosapi.dto.LoginResponse;
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
@RequestMapping("/login")
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<WrapperResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = usuarioService.loginUsuario(loginRequest);
        return new WrapperResponse<>(true, "Login successful", loginResponse).createResponse();
    }

}
