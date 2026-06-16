package com.practica.historias.aplicacion.manejador;

import com.practica.historias.dominio.servicio.ServicioEliminarPersona;
import org.springframework.stereotype.Component;

@Component
public class ManejadorEliminarPersona {

    private final ServicioEliminarPersona servicioEliminarPersona;

    public ManejadorEliminarPersona(ServicioEliminarPersona servicioEliminarPersona) {
        this.servicioEliminarPersona = servicioEliminarPersona;
    }

    public void ejecutar(Long id) {
        this.servicioEliminarPersona.ejecutar(id);
    }
}