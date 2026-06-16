package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.puerto.PersonaRepositorio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ServicioEliminarPersonaTest {

    @Test
    void debeEliminarPersonaCorrectamente() {
        PersonaRepositorio personaRepositorio = Mockito.mock(PersonaRepositorio.class);
        ServicioEliminarPersona servicio = new ServicioEliminarPersona(personaRepositorio);

        servicio.ejecutar(1L);

        verify(personaRepositorio, times(1)).eliminar(1L);
    }
}