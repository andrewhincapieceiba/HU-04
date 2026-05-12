package com.practica.historias.infraestructura.controlador;

import com.practica.historias.aplicacion.manejador.ManejadorCrearPersona;
import com.practica.historias.dominio.modelo.Persona;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personas")
public class PersonaControlador {

    private final ManejadorCrearPersona manejadorCrearPersona;

    public PersonaControlador(ManejadorCrearPersona manejadorCrearPersona) {
        this.manejadorCrearPersona = manejadorCrearPersona;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Persona crear(@RequestBody Persona persona) {
        return this.manejadorCrearPersona.ejecutar(persona);
    }
}