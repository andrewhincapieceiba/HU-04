package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioActualizarPersona;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ManejadorActualizarPersonaTest {

    @Test
    void debeEjecutarActualizacion() {
        ServicioActualizarPersona servicio = Mockito.mock(ServicioActualizarPersona.class);
        FabricaPersona fabrica = Mockito.mock(FabricaPersona.class);
        ManejadorActualizarPersona manejador = new ManejadorActualizarPersona(servicio, fabrica);

        PersonaDTO dto = new PersonaDTO(1L, "Andrew", "Hincapie", "a@mail.com", LocalDate.of(2000, 1, 1));
        Persona persona = new Persona(1L, "Andrew", "Hincapie", "a@mail.com", LocalDate.of(2000, 1, 1));

        when(fabrica.crear(dto)).thenReturn(persona);

        manejador.ejecutar(1L, dto);

        verify(servicio, times(1)).ejecutar(1L, persona);
    }
}