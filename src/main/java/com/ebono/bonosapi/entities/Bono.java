package com.ebono.bonosapi.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Bonos")
public class Bono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private MetodoFinanciero metodoFinanciero;

    @ManyToOne
    private TipoCambio tipoCambio;

    @Column(name = "Valor_Nominal")
    private Double valorNominal;

    @Column(name = "Tasa_Anual")
    private Double tasaAnual;

    @Column(name = "Vencimiento_años")
    private Integer vencimientoAnios;

    @Column(name = "Periodo_pago")
    private String periodoPago;

    @Column(name = "Tasa_Periodo")
    private Double tasaPeriodo;

    @Column(name = "Numero_Periodos")
    private Integer numeroPeriodos;

    @Column(name = "Anualidad")
    private Double anualidad;

    @Column(name = "Valor_Bono")
    private Double valorBono;

    @Column(name = "Van")
    private Double van;

    @Column(name = "Tir")
    private Double tir;

    @Column(name = "Duracion")
    private Double duracion;

    @Column(name = "Duracion_Modificada")
    private Double duracionModificada;

    @Column(name = "Convexidad")
    private Double convexidad;

    @Column(name = "IsPrimario")
    private Boolean isPrimario;

    @Transient
    public List<Double> anualidades;
}
