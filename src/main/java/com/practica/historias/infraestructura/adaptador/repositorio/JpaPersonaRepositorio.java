package com.practica.historias.infraestructura.adaptador.repositorio;



import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de Spring Data JPA para acceso a PostgreSQL.
 */
@Repository
public interface JpaPersonaRepositorio extends JpaRepository<PersonaEntity, Long> {
}