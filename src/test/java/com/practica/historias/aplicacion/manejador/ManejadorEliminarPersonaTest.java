package com.practica.historias.aplicacion.manejador;

import com.practica.historias.dominio.servicio.ServicioEliminarPersona;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ManejadorEliminarPersonaTest {

    @Test
    void debeEjecutarEliminacion() {
        ServicioEliminarPersona servicio = Mockito.mock(ServicioEliminarPersona.class);
        ManejadorEliminarPersona manejador = new ManejadorEliminarPersona(servicio);

        manejador.ejecutar(1L);

        verify(servicio, times(1)).ejecutar(1L);
    }
}