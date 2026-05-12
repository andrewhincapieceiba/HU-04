package com.practica.historias.aplicacion.manejador;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioCrearPersona;

public class ManejadorCrearPersona {

    private final ServicioCrearPersona servicioCrearPersona;

    public ManejadorCrearPersona(ServicioCrearPersona servicioCrearPersona) {
        this.servicioCrearPersona = servicioCrearPersona;
    }

    public Persona ejecutar(Persona persona) {
        return this.servicioCrearPersona.ejecutar(persona);
    }
}