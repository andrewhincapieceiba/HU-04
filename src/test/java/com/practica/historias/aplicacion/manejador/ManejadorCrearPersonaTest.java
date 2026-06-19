package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioCrearPersona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ManejadorCrearPersonaTest {

    private ServicioCrearPersona servicioCrearPersona;
    private FabricaPersona fabricaPersona;
    private ManejadorCrearPersona manejadorCrearPersona;

    @BeforeEach
    void setUp() {
        this.servicioCrearPersona = Mockito.mock(ServicioCrearPersona.class);
        this.fabricaPersona = new FabricaPersona();
        this.manejadorCrearPersona = new ManejadorCrearPersona(servicioCrearPersona, fabricaPersona);
    }

    @Test
    void debeCrearPersonaCorrectamente() {
        PersonaDTO dtoEntrada = new PersonaDTO(null, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));
        Persona personaGuardada = new Persona(1L, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        when(this.servicioCrearPersona.ejecutar(any(Persona.class))).thenReturn(personaGuardada);

        PersonaDTO resultado = this.manejadorCrearPersona.ejecutar(dtoEntrada);

        assertEquals(1L, resultado.getId());
        assertEquals("Andrew", resultado.getNombre());
        assertEquals("Hincapie", resultado.getApellido());
        assertEquals("andrew@mail.com", resultado.getEmail());
    }

    @Test
    void debeLanzarExcepcionNegocioCuandoDatosSonInvalidosAlCrear() {

        PersonaDTO dtoInvalido = new PersonaDTO(null, "   ", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));


        assertThrows(ExcepcionNegocio.class, () -> {
            this.manejadorCrearPersona.ejecutar(dtoInvalido);
        });
    }
}