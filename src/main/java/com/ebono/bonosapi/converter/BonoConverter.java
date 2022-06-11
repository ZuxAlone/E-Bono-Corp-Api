package com.ebono.bonosapi.converter;

import com.ebono.bonosapi.common.EntityDtoConverter;
import com.ebono.bonosapi.dto.BonoResponse;
import com.ebono.bonosapi.entities.Bono;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BonoConverter {

    private final EntityDtoConverter entityDtoConverter;

    public BonoConverter(EntityDtoConverter entityDtoConverter) {
        this.entityDtoConverter = entityDtoConverter;
    }

    public BonoResponse fromEntity(Bono bono) {
        if (bono == null) return null;
        return BonoResponse.builder()
                .id(bono.getId())
                .usuarioResponse(entityDtoConverter.convertEntityToDtoUser(bono.getUsuario()))
                .tipoCambio(bono.getTipoCambio().getTipoCambio())
                .metodoFinanciero(bono.getMetodoFinanciero().getMetodo())
                .valorNominal(bono.getValorNominal())
                .periodoPago(bono.getPeriodoPago())
                .tasaAnual(bono.getTasaAnual())
                .numeroPeriodos(bono.getNumeroPeriodos())
                .anualidad(bono.getAnualidad())
                .van(bono.getVan())
                .tir(bono.getTir())
                .duracion(bono.getDuracion())
                .duracionModificada(bono.getDuracionModificada())
                .convexidad(bono.getConvexidad())
                .build();
    }

    public List<BonoResponse> fromList(List<Bono> bonos) {
        return bonos.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }
}
