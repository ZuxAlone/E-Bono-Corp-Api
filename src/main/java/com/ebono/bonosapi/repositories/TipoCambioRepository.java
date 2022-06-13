package com.ebono.bonosapi.repositories;

import com.ebono.bonosapi.entities.TipoCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCambioRepository extends JpaRepository<TipoCambio, Long> {
    TipoCambio findTipoCambioByTipoCambio(String tipoCambio);
}
