package com.ebono.bonosapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class BonoRequestSec {
    @NotBlank
    @NotNull
    private Double tasaAnualSec;
    @NotBlank
    @NotNull
    private String tipoTasaSec;
    @NotBlank
    @NotNull
    private Integer numPeriodoCompra;
}
