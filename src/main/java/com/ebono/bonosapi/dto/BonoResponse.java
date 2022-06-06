package com.ebono.bonosapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BonoResponse {
    private Long id;
    private Double valorNominal;
    private String periodoPago;
    private Integer numeroPeriodos;
    private Double anualidad;
    private Double van;
    private Double tir;
    private Double duracion;
    private Double duracionModificada;
    private Double convexidad;
}
