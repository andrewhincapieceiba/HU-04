package com.sprint.infraestructura.adaptador.repositorio;

import com.sprint.infraestructura.entidad.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPersonaRepository extends JpaRepository<PersonaEntity, Long> {
}