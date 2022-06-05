package com.ebono.bonosapi.service;

import com.ebono.bonosapi.dto.BonoRequest;
import com.ebono.bonosapi.dto.BonoRequestSec;
import com.ebono.bonosapi.entities.Bono;
import com.ebono.bonosapi.repositories.BonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BonoService {

    private final BonoRepository bonoRepository;

    public BonoService(BonoRepository bonoRepository) {
        this.bonoRepository = bonoRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Bono simularBonoPrimario(BonoRequest bonoRequest) {
        Bono bono = new Bono();
        return bono;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Bono simularBonoSecundario(Long id, BonoRequestSec bonoRequestSec) {
        Bono bono = new Bono();
        return bono;
    }
}
