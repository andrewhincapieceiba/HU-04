package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ServicioListarPersonasTest {

    private PersonaRepositorio personaRepositorio;
    private ServicioListarPersonas servicioListarPersonas;

    @BeforeEach
    void setUp() {
        this.personaRepositorio = Mockito.mock(PersonaRepositorio.class);
        this.servicioListarPersonas = new ServicioListarPersonas(personaRepositorio);
    }

    @Test
    void debeListarTodasLasPersonasExitosamente() {
        Persona personaUno = new Persona(1L, "Carlos", "Alvarez", "carlos@mail.com", LocalDate.of(1995, 4, 12));
        Persona personaDos = new Persona(2L, "Andrew", "Gomez", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        when(this.personaRepositorio.listarTodos()).thenReturn(Arrays.asList(personaUno, personaDos));

        List<Persona> resultado = this.servicioListarPersonas.ejecutar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Alvarez", resultado.get(0).getApellido());
        assertEquals("Gomez", resultado.get(1).getApellido());
    }
}