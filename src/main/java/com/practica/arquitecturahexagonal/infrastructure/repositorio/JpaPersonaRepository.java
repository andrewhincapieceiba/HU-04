package com.practica.arquitecturahexagonal.infrastructure.repositorio;

import com.practica.arquitecturahexagonal.infrastructure.adaptador.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaPersonaRepository extends JpaRepository<PersonaEntity, Long> {
    // Spring Data JPA crea la query automáticamente
    List<PersonaEntity> findAllByOrderByApellidoAsc();
}