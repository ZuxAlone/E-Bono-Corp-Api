package com.ebono.bonosapi.service;

import com.ebono.bonosapi.dto.BonoRequest;
import com.ebono.bonosapi.dto.BonoRequestSec;
import com.ebono.bonosapi.entities.Bono;
import com.ebono.bonosapi.entities.MetodoFinanciero;
import com.ebono.bonosapi.entities.TipoCambio;
import com.ebono.bonosapi.entities.Usuario;
import com.ebono.bonosapi.repositories.BonoRepository;
import com.ebono.bonosapi.repositories.MetodoFinancieroRepository;
import com.ebono.bonosapi.repositories.TipoCambioRepository;
import com.ebono.bonosapi.repositories.UsuarioRepository;
import com.ebono.bonosapi.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BonoService {

    private final UsuarioRepository usuarioRepository;
    private final BonoRepository bonoRepository;
    private final TipoCambioRepository tipoCambioRepository;
    private final MetodoFinancieroRepository metodoFinancieroRepository;

    public BonoService(BonoRepository bonoRepository, TipoCambioRepository tipoCambioRepository, MetodoFinancieroRepository metodoFinancieroRepository, UsuarioRepository usuarioRepository) {
        this.bonoRepository = bonoRepository;
        this.tipoCambioRepository = tipoCambioRepository;
        this.metodoFinancieroRepository = metodoFinancieroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Bono simularBonoPrimario(BonoRequest bonoRequest) {
        Bono bono = buildBono(bonoRequest);
        return bonoRepository.save(bono);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Bono simularBonoSecundario(Long id, BonoRequestSec bonoRequestSec) {
        Bono bonoDb = bonoRepository.getById(id);
        Bono bono = buildBonoSec(bonoDb, bonoRequestSec);
        return bonoRepository.save(bono);
    }

    @Transactional(readOnly = true)
    public List<Bono> findBonosPrimarios() {
        List<Bono> bonos = bonoRepository.findBonosPrimarios();
        return bonos;
    }

    @Transactional(readOnly = true)
    public List<Bono> findBonosSecundarios() {
        List<Bono> bonos = bonoRepository.findBonosSecundarios();
        return bonos;
    }

    private Bono buildBono(BonoRequest bonoRequest) {
        Bono bono = new Bono();

        Usuario usuario = UserPrincipal.getCurrentUser();
        MetodoFinanciero metodoFinanciero = metodoFinancieroRepository.findMetodoFinancieroByMetodo(bonoRequest.getMetodoFinanciero());
        TipoCambio tipoCambio = tipoCambioRepository.findTipoCambioByTipoCambio(bonoRequest.getTipoCambio().charAt(0));

        bono.setUsuario(usuario);
        bono.setMetodoFinanciero(metodoFinanciero);
        bono.setTipoCambio(tipoCambio);

        Double tasaAnual = bonoRequest.getTasaAnual() / 100;
        Double tasaPeriodo = 0.0;
        Integer vencimientoAnios = bonoRequest.getVencimientoAnios();
        Integer numeroPeriodos = 0;

        String periodoPago = bonoRequest.getPeriodoPago();

        switch (periodoPago) {
            case "Anual":
                tasaPeriodo = tasaAnual;
                numeroPeriodos = vencimientoAnios;
                break;
            case "Semestral":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/2) - 1)*100;
                numeroPeriodos = vencimientoAnios * 2;
                break;
            case "Cuatrimestral":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/3) - 1)*100;
                numeroPeriodos = vencimientoAnios * 3;
                break;
            case "Trimestral":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/4) - 1)*100;
                numeroPeriodos = vencimientoAnios * 4;
                break;
            case "Bimestral":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/6) - 1)*100;
                numeroPeriodos = vencimientoAnios * 6;
                break;
            case "Mensual":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/12) - 1)*100;
                numeroPeriodos = vencimientoAnios * 12;
                break;
        }

        Double valorNominal = bonoRequest.getValorNominal();
        Double anualidad = (valorNominal * tasaPeriodo/100) / (1.0 - Math.pow(1.0 + tasaPeriodo/100, -numeroPeriodos));
        bono.anualidades = new ArrayList<Double>(Collections.nCopies(numeroPeriodos, anualidad));
        Double valorBono = anualidad * ((1.0 - Math.pow(1.0 + tasaPeriodo/100, -numeroPeriodos)) / (tasaPeriodo/100));
        Double van = calcularVan(valorBono, tasaPeriodo, bono.anualidades);
        Double tir = tasaPeriodo;
        Double duracion = calcularDuracion(valorBono, tir, bono.anualidades);
        Double duracionModificada = calcularDuracionModificada(duracion, tir);
        Double convexidad = calcularConvexidad(valorBono, tir, bono.anualidades);

        bono.setValorNominal(valorNominal);
        bono.setTasaAnual(tasaAnual);
        bono.setVencimientoAnios(vencimientoAnios);
        bono.setPeriodoPago(periodoPago);
        bono.setTasaPeriodo(tasaPeriodo);
        bono.setNumeroPeriodos(numeroPeriodos);
        bono.setAnualidad(anualidad);
        bono.setValorBono(valorBono);
        bono.setVan(van);
        bono.setTir(tir);
        bono.setDuracion(duracion);
        bono.setDuracionModificada(duracionModificada);
        bono.setConvexidad(convexidad);
        bono.setIsPrimario(true);

        usuario.setNum_bonos_simulados(usuario.getNum_bonos_simulados()+1);
        usuarioRepository.save(usuario);

        return bono;
    }

    private Bono buildBonoSec(Bono bonoDb, BonoRequestSec bonoRequestSec) {
        Bono bono = new Bono();

        Usuario usuario = UserPrincipal.getCurrentUser();
        bono.setUsuario(usuario);
        bono.setMetodoFinanciero(bonoDb.getMetodoFinanciero());
        bono.setTipoCambio(bonoDb.getTipoCambio());

        Double tasaAnual = bonoRequestSec.getTasaAnualSec() / 100;
        Double tasaPeriodo = 0.0;
        Integer periodoCompra = bonoRequestSec.getNumPeriodoCompra();
        Integer periodosRestantes = bonoDb.getNumeroPeriodos() - periodoCompra;

        Double anualidad = bonoDb.getAnualidad();
        bono.anualidades = new ArrayList<Double>(Collections.nCopies(periodosRestantes, anualidad));

        String periodoPago = bonoDb.getPeriodoPago();

        switch (periodoPago) {
            case "Anual":
                tasaPeriodo = tasaAnual;
                break;
            case "Semestral":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/2) - 1)*100;
                break;
            case "Cuatrimestral":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/3) - 1)*100;
                break;
            case "Trimestral":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/4) - 1)*100;
                break;
            case "Bimestral":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/6) - 1)*100;
                break;
            case "Mensual":
                tasaPeriodo = (Math.pow(1.0+tasaAnual, 1.0/12) - 1)*100;
                break;
        }

        Double tasaPeriodoOrig = bonoDb.getTasaPeriodo();
        Double valorBonoOrg = anualidad * ((1.0 - Math.pow(1.0 + tasaPeriodoOrig/100, -periodosRestantes)) / (tasaPeriodoOrig/100));
        Double valorBono = anualidad * ((1.0 - Math.pow(1.0 + tasaPeriodo/100, -periodosRestantes)) / (tasaPeriodo/100));
        Double van = valorBonoOrg - valorBono;
        Double tir = tasaPeriodo;
        Double duracion = calcularDuracion(valorBono, tir, bono.anualidades);
        Double duracionModificada = calcularDuracionModificada(duracion, tir);
        Double convexidad = calcularConvexidad(valorBono, tir, bono.anualidades);

        bono.setValorNominal(valorBono);
        bono.setTasaAnual(tasaAnual);
        bono.setVencimientoAnios(bonoDb.getVencimientoAnios());
        bono.setPeriodoPago(periodoPago);
        bono.setTasaPeriodo(tasaPeriodo);
        bono.setNumeroPeriodos(periodosRestantes);
        bono.setAnualidad(anualidad);
        bono.setValorBono(valorBono);
        bono.setVan(van);
        bono.setTir(tir);
        bono.setDuracion(duracion);
        bono.setDuracionModificada(duracionModificada);
        bono.setConvexidad(convexidad);
        bono.setIsPrimario(false);

        usuario.setNum_bonos_simulados(usuario.getNum_bonos_simulados()+1);
        usuarioRepository.save(usuario);

        return bono;
    }

    private Double calcularVan(Double valorBono, Double tasaPeriodo, List<Double> anualidades) {
        Double van = valorBono;
        for (int i = 0; i < anualidades.size(); i++)
            van -= anualidades.get(i) / Math.pow(1.0 + tasaPeriodo/100, i+1);
        if (van < 0.001)  van = 0.0;
        return van;
    }

    private Double calcularDuracion(Double valorBono, Double tir, List<Double> anualidades) {
        Double duracion = 0.0;
        for (int i = 0; i<anualidades.size(); i++)
            duracion += ((i+1) * anualidades.get(i)) / Math.pow(1.0 + tir/100, i+1);
        duracion /= valorBono;
        return duracion;
    }

    private Double calcularDuracionModificada(Double duracion, Double tir) {
        Double duracionModificada = duracion;
        duracionModificada /= (1.0 + tir/100);
        return duracionModificada;
    }

    private Double calcularConvexidad(Double valorBono, Double tir, List<Double> anualidades) {
        Double convexidad = 0.0;
        for (int i = 0; i<anualidades.size(); i++)
            convexidad += ((Math.pow(i+1, 2) + i+1) * anualidades.get(i)) / Math.pow(1.0 + tir, i+1);
        convexidad /= (valorBono * Math.pow(1.0 + tir/100, 2));
        return convexidad;
    }
}
