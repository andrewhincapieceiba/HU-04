package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;


public class ServicioCrearPersona {

    private final PersonaRepositorio personaRepositorio;

    public ServicioCrearPersona(PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    public Persona ejecutar(Persona persona) {
        // Llamamos al repositorio y devolvemos el objeto Persona que él nos entrega
        return this.personaRepositorio.guardar(persona);
    }
}