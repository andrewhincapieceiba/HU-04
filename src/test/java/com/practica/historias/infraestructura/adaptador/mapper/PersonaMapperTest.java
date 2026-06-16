package com.practica.historias.infraestructura.adaptador.mapper;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersonaMapperTest {

    private final PersonaMapper personaMapper = new PersonaMapper();

    @Test
    void debeInstanciarMapperCorrectamente() {
        PersonaMapper mapper = new PersonaMapper();
        assertNotNull(mapper);
    }

    @Test
    void debeConvertirDominioAEntidad() {
        Persona persona = new Persona(1L, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        PersonaEntity entidad = this.personaMapper.aEntidad(persona);

        assertNotNull(entidad);
        assertEquals(1L, entidad.getId());
        assertEquals("Andrew", entidad.getNombre());
        assertEquals("Hincapie", entidad.getApellido());
        assertEquals("andrew@mail.com", entidad.getEmail());
        assertEquals(LocalDate.of(2000, 1, 1), entidad.getFechaNacimiento());
    }

    @Test
    void debeConvertirEntidadADominio() {
        PersonaEntity entidad = new PersonaEntity();
        entidad.setId(2L);
        entidad.setNombre("Luis");
        entidad.setApellido("Diaz");
        entidad.setEmail("luis@mail.com");
        entidad.setFechaNacimiento(LocalDate.of(1997, 1, 13));

        Persona persona = this.personaMapper.aDominio(entidad);

        assertNotNull(persona);
        assertEquals(2L, persona.getId());
        assertEquals("Luis", persona.getNombre());
        assertEquals("Diaz", persona.getApellido());
        assertEquals("luis@mail.com", persona.getEmail());
        assertEquals(LocalDate.of(1997, 1, 13), persona.getFechaNacimiento());
    }
}