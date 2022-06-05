package com.ebono.bonosapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class BonoRequest {
    @NotBlank
    @NotNull
    private Double valorNominal;
    @NotBlank
    @NotNull
    private Double tasaAnual;
    @NotBlank
    @NotNull
    private String periodoPago;
    @NotBlank
    @NotNull
    private Integer vencimientoAnios;

    @NotBlank
    @NotNull
    private String metodoFinanciero;
    @NotBlank
    @NotNull
    private String tipoCambio;
}
