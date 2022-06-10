package com.ebono.bonosapi.controller;

import com.ebono.bonosapi.converter.BonoConverter;
import com.ebono.bonosapi.dto.BonoRequest;
import com.ebono.bonosapi.dto.BonoRequestSec;
import com.ebono.bonosapi.dto.BonoResponse;
import com.ebono.bonosapi.entities.Bono;
import com.ebono.bonosapi.service.BonoService;
import com.ebono.bonosapi.utils.WrapperResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/bono")
public class BonoController {

    private final BonoService bonoService;
    private final BonoConverter bonoConverter;

    public BonoController(BonoService bonoService, BonoConverter bonoConverter) {
        this.bonoService = bonoService;
        this.bonoConverter = bonoConverter;
    }

    @PostMapping("/primario")
    public ResponseEntity<BonoResponse> simularBonoPrimario(@RequestBody BonoRequest bonoRequest) {
        Bono bono = bonoService.simularBonoPrimario(bonoRequest);
        return new ResponseEntity<>(bonoConverter.fromEntity(bono), HttpStatus.CREATED);
    }

    @PostMapping("/secundario/{id}")
    public ResponseEntity<BonoResponse> simularBonoSecundario(@PathVariable Long id, @RequestBody BonoRequestSec bonoRequestSec) {
        Bono bono = bonoService.simularBonoSecundario(id, bonoRequestSec);
        return new ResponseEntity<>(bonoConverter.fromEntity(bono), HttpStatus.CREATED);
    }

    @GetMapping("/primarios")
    public ResponseEntity<List<BonoResponse>> findBonosPrimarios() throws Exception {
        List<Bono> bonos = bonoService.findBonosPrimarios();
        return new ResponseEntity<>(bonoConverter.fromList(bonos), HttpStatus.OK);
    }

    @GetMapping("/secundarios")
    public ResponseEntity<List<BonoResponse>> findBonosSecundarios() throws Exception {
        List<Bono> bonos = bonoService.findBonosSecundarios();
        return new ResponseEntity<>(bonoConverter.fromList(bonos), HttpStatus.OK);
    }
}
