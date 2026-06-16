package com.practica.historias.aplicacion.manejador.fabrica;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.modelo.Persona;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FabricaPersonaTest {

    private final FabricaPersona fabricaPersona = new FabricaPersona();

    @Test
    void debeInstanciarFabricaCorrectamente() {
        FabricaPersona fabrica = new FabricaPersona();
        assertNotNull(fabrica);
    }

    @Test
    void debeCrearPersonaDesdeDto() {
        PersonaDTO dto = new PersonaDTO(1L, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        Persona persona = this.fabricaPersona.crear(dto);

        assertNotNull(persona);
        assertEquals(dto.getId(), persona.getId());
        assertEquals(dto.getNombre(), persona.getNombre());
        assertEquals(dto.getApellido(), persona.getApellido());
        assertEquals(dto.getEmail(), persona.getEmail());
        assertEquals(dto.getFechaNacimiento(), persona.getFechaNacimiento());
    }

    @Test
    void debeCrearDtoDesdePersona() {
        Persona persona = new Persona(1L, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        PersonaDTO dto = this.fabricaPersona.crearDTO(persona);

        assertNotNull(dto);
        assertEquals(persona.getId(), dto.getId());
        assertEquals(persona.getNombre(), dto.getNombre());
        assertEquals(persona.getApellido(), dto.getApellido());
        assertEquals(persona.getEmail(), dto.getEmail());
        assertEquals(persona.getFechaNacimiento(), dto.getFechaNacimiento());
    }
}