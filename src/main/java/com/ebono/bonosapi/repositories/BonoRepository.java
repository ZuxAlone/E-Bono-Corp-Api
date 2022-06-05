package com.ebono.bonosapi.repositories;

import com.ebono.bonosapi.entities.Bono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonoRepository extends JpaRepository<Bono, Long> {
}
