package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioListarPersonas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ManejadorListarPersonasTest {

    private ServicioListarPersonas servicioListarPersonas;
    private FabricaPersona fabricaPersona;
    private ManejadorListarPersonas manejadorListarPersonas;

    @BeforeEach
    void setUp() {
        this.servicioListarPersonas = Mockito.mock(ServicioListarPersonas.class);
        this.fabricaPersona = Mockito.mock(FabricaPersona.class);
        this.manejadorListarPersonas = new ManejadorListarPersonas(servicioListarPersonas, fabricaPersona);
    }

    @Test
    void debeRetornarListaVaciaCuandoNoExistenPersonas() {
        when(this.servicioListarPersonas.ejecutar()).thenReturn(new ArrayList<>());

        List<PersonaDTO> resultado = this.manejadorListarPersonas.ejecutar();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void debeRetornarListaDePersonas() {
        Persona personaUno = new Persona(1L, "Carlos", "Zapata", "carlos@mail.com", LocalDate.of(1990, 1, 1));
        Persona personaDos = new Persona(2L, "Ana", "Alvarez", "ana@mail.com", LocalDate.of(1993, 5, 12));

        when(this.servicioListarPersonas.ejecutar()).thenReturn(Arrays.asList(personaUno, personaDos));
        when(this.fabricaPersona.crearDTO(personaUno))
                .thenReturn(new PersonaDTO(1L, "Carlos", "Zapata", "carlos@mail.com", LocalDate.of(1990, 1, 1)));
        when(this.fabricaPersona.crearDTO(personaDos))
                .thenReturn(new PersonaDTO(2L, "Ana", "Alvarez", "ana@mail.com", LocalDate.of(1993, 5, 12)));

        List<PersonaDTO> resultado = this.manejadorListarPersonas.ejecutar();

        assertEquals(2, resultado.size());
    }
}