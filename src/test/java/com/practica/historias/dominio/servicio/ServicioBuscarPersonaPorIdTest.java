package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ServicioBuscarPersonaPorIdTest {

    private PersonaRepositorio personaRepositorio;
    private ServicioBuscarPersonaPorId servicioBuscarPersonaPorId;

    @BeforeEach
    void setUp() {
        this.personaRepositorio = Mockito.mock(PersonaRepositorio.class);
        this.servicioBuscarPersonaPorId = new ServicioBuscarPersonaPorId(personaRepositorio);
    }

    @Test
    void debeRetornarPersonaPorId() {
        Persona persona = new Persona(1L, "Andrew", "Gomez", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        when(this.personaRepositorio.buscarPorId(1L)).thenReturn(persona);

        Persona resultado = this.servicioBuscarPersonaPorId.ejecutar(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Andrew", resultado.getNombre());
    }
}