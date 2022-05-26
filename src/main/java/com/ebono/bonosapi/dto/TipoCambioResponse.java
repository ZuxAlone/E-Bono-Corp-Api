package com.ebono.bonosapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoCambioResponse {
    private Long id;
    private Character tipoCambio;
    private String detalle;
}
