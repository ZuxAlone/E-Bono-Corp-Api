package com.ebono.bonosapi.repositories;

import com.ebono.bonosapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = "SELECT * FROM usuarios usuario WHERE usuario.correo=?1", nativeQuery = true)
    Optional<Usuario> findByCorreo(String correo);
}
