package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;

public class ServicioBuscarPersonaPorId {

    private final PersonaRepositorio personaRepositorio;

    public ServicioBuscarPersonaPorId(PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    public Persona ejecutar(Long id) {
        return this.personaRepositorio.buscarPorId(id);
    }
}