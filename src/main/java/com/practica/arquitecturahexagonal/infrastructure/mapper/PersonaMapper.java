package com.practica.arquitecturahexagonal.infrastructure.mapper;


import com.practica.arquitecturahexagonal.domain.model.Persona;
import com.practica.arquitecturahexagonal.infrastructure.adaptador.PersonaEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonaMapper {

    public PersonaEntity aEntidad(Persona persona) {
        PersonaEntity entidad = new PersonaEntity();
        entidad.setId(persona.getId());
        entidad.setNombre(persona.getNombre());
        entidad.setApellido(persona.getApellido());
        entidad.setEmail(persona.getEmail());
        entidad.setFechaNacimiento(persona.getFechaNacimiento());
        return entidad;
    }

    public Persona aDominio(PersonaEntity entidad) {
        return new Persona(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getApellido(),
                entidad.getEmail(),
                entidad.getFechaNacimiento()
        );
    }
}