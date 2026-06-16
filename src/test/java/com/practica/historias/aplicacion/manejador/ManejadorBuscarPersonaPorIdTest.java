package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioBuscarPersonaPorId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ManejadorBuscarPersonaPorIdTest {

    private ServicioBuscarPersonaPorId servicioBuscarPersonaPorId;
    private FabricaPersona fabricaPersona;
    private ManejadorBuscarPersonaPorId manejador;

    @BeforeEach
    void setUp() {
        this.servicioBuscarPersonaPorId = Mockito.mock(ServicioBuscarPersonaPorId.class);
        this.fabricaPersona = new FabricaPersona();
        this.manejador = new ManejadorBuscarPersonaPorId(servicioBuscarPersonaPorId, fabricaPersona);
    }

    @Test
    void debeRetornarPersonaDTO() {
        Persona persona = new Persona(1L, "Andrew", "Gomez", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        when(this.servicioBuscarPersonaPorId.ejecutar(1L)).thenReturn(persona);

        PersonaDTO resultado = this.manejador.ejecutar(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Andrew", resultado.getNombre());
    }
}