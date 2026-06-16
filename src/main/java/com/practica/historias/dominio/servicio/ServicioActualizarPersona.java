package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;

public class ServicioActualizarPersona {

    private final PersonaRepositorio personaRepositorio;

    public ServicioActualizarPersona(PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    public Persona ejecutar(Long id, Persona persona) {
        return this.personaRepositorio.actualizar(id, persona);
    }
}