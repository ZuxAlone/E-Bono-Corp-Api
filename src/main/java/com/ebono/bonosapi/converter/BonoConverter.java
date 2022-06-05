package com.ebono.bonosapi.converter;

import com.ebono.bonosapi.dto.BonoResponse;
import com.ebono.bonosapi.entities.Bono;
import org.springframework.stereotype.Component;

@Component
public class BonoConverter {

    public BonoResponse fromEntity(Bono bono) {
        if (bono == null) return null;
        return BonoResponse.builder()
                .id(bono.getId())
                .valorNominal(bono.getValorNominal())
                .anualidad(bono.getAnualidad())
                .van(bono.getVan())
                .tir(bono.getTir())
                .duracion(bono.getDuracion())
                .duracionModificada(bono.getDuracionModificada())
                .convexidad(bono.getConvexidad())
                .build();
    }
}
