package com.practica.historias.infraestructura.adaptador.repositorio;

import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaPersonaRepositorio extends JpaRepository<PersonaEntity, Long> {
    // Genera automáticamente la consulta: SELECT * FROM ... ORDER BY apellido ASC
    List<PersonaEntity> findAllByOrderByApellidoAsc();
}