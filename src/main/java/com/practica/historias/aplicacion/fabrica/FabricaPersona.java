package com.practica.historias.aplicacion.fabrica;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.dominio.modelo.Persona;
import org.springframework.stereotype.Component;

@Component
public class FabricaPersona {

    public Persona crear(PersonaDTO dto) {
        return new Persona(
                dto.getId(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail(),
                dto.getFechaNacimiento()
        );
    }
}