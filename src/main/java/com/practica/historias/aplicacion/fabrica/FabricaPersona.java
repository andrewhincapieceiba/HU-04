package com.practica.historias.aplicacion.fabrica;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.dominio.modelo.Persona;
import org.springframework.stereotype.Component;

@Component
public class FabricaPersona {

    // Convierte de DTO (Vista/API) a Modelo de Dominio (Negocio)
    public Persona crear(PersonaDTO dto) {
        return new Persona(
                dto.getId(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail(),
                dto.getFechaNacimiento()
        );
    }

    // Convierte de Modelo de Dominio (Negocio) a DTO (Vista/API)
    public PersonaDTO crearDTO(Persona persona) {
        return new PersonaDTO(
                persona.getId(),
                persona.getNombre(),
                persona.getApellido(),
                persona.getEmail(),
                persona.getFechaNacimiento()
        );
    }
}