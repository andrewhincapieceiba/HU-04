package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;

import java.util.List;

public class ServicioListarPersonas {

    private final PersonaRepositorio personaRepositorio;

    public ServicioListarPersonas(PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    public List<Persona> ejecutar() {
        return this.personaRepositorio.listarTodos();
    }
}