package com.practica.arquitecturahexagonal.infrastructure.adaptador.salida;

import com.practica.arquitecturahexagonal.domain.model.Persona;
import com.practica.arquitecturahexagonal.domain.puerto.PersonaRepositoryPort;
import com.practica.arquitecturahexagonal.infrastructure.adaptador.PersonaEntity;
import com.practica.arquitecturahexagonal.infrastructure.mapper.PersonaMapper;
import com.practica.arquitecturahexagonal.infrastructure.repositorio.JpaPersonaRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PersonaPersistenceAdapter implements PersonaRepositoryPort {

    private final JpaPersonaRepository jpaRepository;
    private final PersonaMapper personaMapper;

    public PersonaPersistenceAdapter(JpaPersonaRepository jpaRepository, PersonaMapper personaMapper) {
        this.jpaRepository = jpaRepository;
        this.personaMapper = personaMapper;
    }

    @Override
    public Persona guardar(Persona persona) {
        PersonaEntity entity = personaMapper.aEntidad(persona);
        return personaMapper.aDominio(jpaRepository.save(entity));
    }

    @Override
    public List<Persona> obtenerTodas() {
        return jpaRepository.findAllByOrderByApellidoAsc()
                .stream()
                .map(personaMapper::aDominio)
                .toList();
    }
}