package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServicioActualizarPersonaTest {

    @Test
    void debeActualizarPersonaCorrectamente() {
        PersonaRepositorio personaRepositorio = mock(PersonaRepositorio.class);
        ServicioActualizarPersona servicio = new ServicioActualizarPersona(personaRepositorio);
        Persona persona = new Persona(1L, "Andrew", "Hincapie Actualizado", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        when(personaRepositorio.actualizar(1L, persona)).thenReturn(persona);

        Persona resultado = servicio.ejecutar(1L, persona);

        assertEquals("Andrew", resultado.getNombre());
        assertEquals("Hincapie Actualizado", resultado.getApellido());
        verify(personaRepositorio, times(1)).actualizar(1L, persona);
    }
}