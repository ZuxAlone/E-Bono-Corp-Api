package com.ebono.bonosapi.repositories;

import com.ebono.bonosapi.entities.Bono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonoRepository extends JpaRepository<Bono, Long> {

    @Query(value = "SELECT bono FROM Bono bono WHERE bono.isPrimario=true")
    List<Bono> findBonosPrimarios();

    @Query(value = "SELECT bono FROM Bono bono WHERE bono.isPrimario=false")
    List<Bono> findBonosSecundarios();
}
