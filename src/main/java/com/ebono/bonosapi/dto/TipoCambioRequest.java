package com.ebono.bonosapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TipoCambioRequest {
    private String tipoCambio;
    private String detalle;
}
