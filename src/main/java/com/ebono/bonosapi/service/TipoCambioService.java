package com.ebono.bonosapi.service;

import com.ebono.bonosapi.dto.TipoCambioRequest;
import com.ebono.bonosapi.entities.TipoCambio;
import com.ebono.bonosapi.exception.ResourceNotFoundException;
import com.ebono.bonosapi.repositories.TipoCambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoCambioService {

    private final TipoCambioRepository tipoCambioRepository;

    public TipoCambioService(TipoCambioRepository tipoCambioRepository) {
        this.tipoCambioRepository = tipoCambioRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public TipoCambio addTipoCambio(TipoCambioRequest tipoCambioRequest) {
        TipoCambio tipoCambio = new TipoCambio();
        tipoCambio.setTipo_cambio(tipoCambioRequest.getTipo_cambio());
        tipoCambio.setDetalle(tipoCambioRequest.getDetalle());
        return tipoCambioRepository.save(tipoCambio);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public TipoCambio updateTipoCambio(Long id, TipoCambioRequest tipoCambioRequest){
        TipoCambio tipoCambio = tipoCambioRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.byIndex("Tipo Cambio", id));
        tipoCambio.setTipo_cambio(tipoCambioRequest.getTipo_cambio());
        tipoCambio.setDetalle(tipoCambioRequest.getDetalle());
        return tipoCambioRepository.save(tipoCambio);
    }

    @Transactional(readOnly = true)
    public TipoCambio getTipoCambio(Long id) {
        TipoCambio tipoCambio = tipoCambioRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.byIndex("Tipo Cambio", id));
        return tipoCambio;
    }

    @Transactional
    public void deleteTipoCambio(Long id){
        TipoCambio tipoCambio = tipoCambioRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.byIndex("Tipo Cambio", id));
        tipoCambioRepository.delete(tipoCambio);
    }
}
