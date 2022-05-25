package com.ebono.bonosapi.repositories;

import com.ebono.bonosapi.entities.MetodoFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoFinancieroRepository extends JpaRepository<MetodoFinanciero, Long> {
    MetodoFinanciero findMetodoFinancieroByMetodo(String metodo);
}
