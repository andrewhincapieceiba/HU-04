package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ServicioCrearPersonaTest {

    private PersonaRepositorio personaRepositorio;
    private ServicioCrearPersona servicioCrearPersona;

    @BeforeEach
    void setUp() {
        this.personaRepositorio = Mockito.mock(PersonaRepositorio.class);
        this.servicioCrearPersona = new ServicioCrearPersona(personaRepositorio);
    }

    @Test
    void debeCrearPersonaEnDominioExitosamente() {
        Persona personaParaGuardar = new Persona(null, "Andrew", "Gomez", "andrew@mail.com", LocalDate.of(2000, 1, 1));
        Persona personaGuardada = new Persona(1L, "Andrew", "Gomez", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        when(this.personaRepositorio.guardar(any(Persona.class))).thenReturn(personaGuardada);

        Persona resultado = this.servicioCrearPersona.ejecutar(personaParaGuardar);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("andrew@mail.com", resultado.getEmail());
    }
}