package com.ebono.bonosapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetodoFinancieroResponse {
    private Long id;
    private String metodo;
    private String detalle;
}
