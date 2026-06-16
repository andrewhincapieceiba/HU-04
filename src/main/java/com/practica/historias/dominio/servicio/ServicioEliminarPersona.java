package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.puerto.PersonaRepositorio;

public class ServicioEliminarPersona {
    private final PersonaRepositorio personaRepositorio;
    public ServicioEliminarPersona(PersonaRepositorio personaRepositorio)
    {
        this.personaRepositorio = personaRepositorio;

    } public void ejecutar(Long id) { this.personaRepositorio.eliminar(id); }
}
