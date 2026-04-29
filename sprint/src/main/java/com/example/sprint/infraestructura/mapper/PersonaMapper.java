package com.sprint.infraestructura.mapper;

import com.sprint.dominio.modelo.Persona;
import com.sprint.infraestructura.entidad.PersonaEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {

    public PersonaEntity toEntity(Persona persona) {
        PersonaEntity entity = new PersonaEntity();
        entity.setId(persona.getId());
        entity.setNombre(persona.getNombre());
        entity.setApellido(persona.getApellido());
        entity.setEmail(persona.getEmail());
        entity.setFechaNacimiento(persona.getFechaNacimiento());
        return entity;
    }

    public Persona toDomain(PersonaEntity entity) {
        return new Persona(
            entity.getId(),
            entity.getNombre(),
            entity.getApellido(),
            entity.getEmail(),
            entity.getFechaNacimiento()
        );
    }
}