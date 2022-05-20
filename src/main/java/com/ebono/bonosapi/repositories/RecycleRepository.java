package com.ebono.bonosapi.repositories;

import com.ebono.bonosapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecycleRepository extends JpaRepository<Usuario, Long> {
}
