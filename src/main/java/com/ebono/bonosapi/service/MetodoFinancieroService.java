package com.ebono.bonosapi.service;

import com.ebono.bonosapi.dto.MetodoFinancieroRequest;
import com.ebono.bonosapi.entities.MetodoFinanciero;
import com.ebono.bonosapi.exception.ResourceNotFoundException;
import com.ebono.bonosapi.repositories.MetodoFinancieroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MetodoFinancieroService {

    private final MetodoFinancieroRepository metodoFinancieroRepository;

    public MetodoFinancieroService(MetodoFinancieroRepository metodoFinancieroRepository) {
        this.metodoFinancieroRepository = metodoFinancieroRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public MetodoFinanciero addMetodoFinanciero(MetodoFinancieroRequest metodoFinancieroRequest) {
        MetodoFinanciero metodoFinanciero = new MetodoFinanciero();
        metodoFinanciero.setMetodo(metodoFinancieroRequest.getMetodo());
        metodoFinanciero.setDetalle(metodoFinancieroRequest.getDetalle());
        return metodoFinancieroRepository.save(metodoFinanciero);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public MetodoFinanciero updateMetodoFinanciero(Long id, MetodoFinancieroRequest metodoFinancieroRequest){
        MetodoFinanciero metodoFinanciero = metodoFinancieroRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.byIndex("Método Financiero", id));
        metodoFinanciero.setMetodo(metodoFinancieroRequest.getMetodo());
        metodoFinanciero.setDetalle(metodoFinancieroRequest.getDetalle());
        return metodoFinancieroRepository.save(metodoFinanciero);
    }

    @Transactional(readOnly = true)
    public MetodoFinanciero getMetodoFinanciero(Long id) {
        MetodoFinanciero metodoFinanciero = metodoFinancieroRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.byIndex("Método Financiero", id));
        return metodoFinanciero;
    }

    @Transactional(readOnly = true)
    public MetodoFinanciero getMetodoFinancieroByMetodo(String metodo) {
        MetodoFinanciero metodoFinanciero = metodoFinancieroRepository.findMetodoFinancieroByMetodo(metodo);
        if (metodoFinanciero == null) throw new ResourceNotFoundException("No existe el método financiero");
        return metodoFinanciero;
    }

    @Transactional(readOnly = true)
    public List<MetodoFinanciero> getMetodosFinancieros() {
        List<MetodoFinanciero> metodos = metodoFinancieroRepository.findAll();
        return metodos;
    }

    @Transactional
    public void deleteMetodoFinanciero(Long id){
        MetodoFinanciero metodoFinanciero = metodoFinancieroRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.byIndex("Método Financiero", id));
        metodoFinancieroRepository.delete(metodoFinanciero);
    }
}
