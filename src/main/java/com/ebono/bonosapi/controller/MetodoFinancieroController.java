package com.ebono.bonosapi.controller;

import com.ebono.bonosapi.common.EntityDtoConverter;
import com.ebono.bonosapi.dto.MetodoFinancieroRequest;
import com.ebono.bonosapi.dto.MetodoFinancieroResponse;
import com.ebono.bonosapi.entities.MetodoFinanciero;
import com.ebono.bonosapi.service.MetodoFinancieroService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/metodoFinanciero")
public class MetodoFinancieroController {

    private final MetodoFinancieroService metodoFinancieroService;
    private final EntityDtoConverter entityDtoConverter;

    public MetodoFinancieroController(MetodoFinancieroService metodoFinancieroService, EntityDtoConverter entityDtoConverter) {
        this.metodoFinancieroService = metodoFinancieroService;
        this.entityDtoConverter = entityDtoConverter;
    }

    @PostMapping
    public ResponseEntity<MetodoFinancieroResponse> addMetodoFinanciero(@RequestBody MetodoFinancieroRequest metodoFinancieroRequest) throws Exception {
        MetodoFinanciero metodoFinanciero = metodoFinancieroService.addMetodoFinanciero(metodoFinancieroRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoMetodoFinanciero(metodoFinanciero), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoFinancieroResponse> updateMetodoFinanciero(@PathVariable Long id, @RequestBody MetodoFinancieroRequest metodoFinancieroRequest) throws Exception {
        MetodoFinanciero metodoFinanciero = metodoFinancieroService.updateMetodoFinanciero(id, metodoFinancieroRequest);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoMetodoFinanciero(metodoFinanciero), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoFinancieroResponse> getMetodoFinanciero(@PathVariable Long id) throws Exception {
        MetodoFinanciero metodoFinanciero = metodoFinancieroService.getMetodoFinanciero(id);
        return new ResponseEntity<>(entityDtoConverter.convertEntityToDtoMetodoFinanciero(metodoFinanciero), HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteMetodoFinanciero(@PathVariable Long id) throws Exception {
        metodoFinancieroService.deleteMetodoFinanciero(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
