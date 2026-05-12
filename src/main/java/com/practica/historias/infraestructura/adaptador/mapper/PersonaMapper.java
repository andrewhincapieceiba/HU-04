package com.practica.historias.infraestructura.adaptador.mapper;

import com.practica.historias.dominio.modelo.Persona;
// Importación corregida a la carpeta de entidad de infraestructura


import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
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