package com.ebono.bonosapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BonoResponse {
    private Long id;
    private UsuarioResponse usuarioResponse;
    private Character tipoCambio;
    private String metodoFinanciero;
    private Double valorNominal;
    private String periodoPago;
    private Double tasaAnual;
    private Integer numeroPeriodos;
    private Double anualidad;
    private Double van;
    private Double tir;
    private Double duracion;
    private Double duracionModificada;
    private Double convexidad;
}
