package com.practica.historias.infraestructura.adaptador.repositorio;

import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JpaPersonaRepositorio extends JpaRepository<PersonaEntity, Long>, JpaSpecificationExecutor<PersonaEntity> {
    List<PersonaEntity> findAllByOrderByApellidoAsc();
    boolean existsByEmail(String email);
}