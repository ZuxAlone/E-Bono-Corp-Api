package com.ebono.bonosapi.controller;

import com.ebono.bonosapi.common.EntityDtoConverter;
import com.ebono.bonosapi.dto.TipoCambioRequest;
import com.ebono.bonosapi.dto.TipoCambioResponse;
import com.ebono.bonosapi.entities.TipoCambio;
import com.ebono.bonosapi.service.TipoCambioService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/tipoCambio")
public class TipoCambioController {

    private final TipoCambioService tipoCambioService;
    private final EntityDtoConverter entityDtoConverter;

    public TipoCambioController(TipoCambioService tipoCambioService, EntityDtoConverter entityDtoConverter) {
        this.tipoCambioService = tipoCambioService;
        this.entityDtoConverter = entityDtoConverter;
    }

    @PostMapping
    public ResponseEntity<TipoCambioResponse> addTipoCambio(@RequestBody TipoCambioRequest tipoCambioRequest) throws Exception {
        TipoCambio tipoCambio = tipoCambioService.addTipoCambio(tipoCambioRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoTipoCambio(tipoCambio), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCambioResponse> updateTipoCambio(@PathVariable Long id, @RequestBody TipoCambioRequest tipoCambioRequest) throws Exception {
        TipoCambio tipoCambio = tipoCambioService.updateTipoCambio(id, tipoCambioRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoTipoCambio(tipoCambio), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCambioResponse> getTipoCambio(@PathVariable Long id) throws Exception {
        TipoCambio tipoCambio = tipoCambioService.getTipoCambio(id);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoTipoCambio(tipoCambio), HttpStatus.OK);
    }

    @GetMapping("/get/{moneda}")
    public ResponseEntity<TipoCambioResponse> getTipoCambioByMoneda(@PathVariable Character moneda) throws Exception {
        TipoCambio tipoCambio = tipoCambioService.getTipoCambioByMoneda(moneda);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoTipoCambio(tipoCambio), HttpStatus.OK);
    }

    @GetMapping("/getCambios")
    public ResponseEntity<List<TipoCambioResponse>> getTipoCambios() throws Exception {
        List<TipoCambio> cambios = tipoCambioService.getTipoCambios();
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoTipoCambio(cambios), HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteTipoCambio(@PathVariable Long id) throws Exception {
        tipoCambioService.deleteTipoCambio(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
