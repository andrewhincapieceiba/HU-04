package com.practica.arquitecturahexagonal.infrastructure.adaptador.entrada;

import com.practica.arquitecturahexagonal.aplicacion.manejador.ManejadorCrearPersona;
import com.practica.arquitecturahexagonal.aplicacion.manejador.ManejadorListarPersonas;
import com.practica.arquitecturahexagonal.domain.model.Persona;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final ManejadorCrearPersona manejadorCrearPersona;
    private final ManejadorListarPersonas manejadorListarPersonas;

    public PersonaController(ManejadorCrearPersona manejadorCrearPersona,
                             ManejadorListarPersonas manejadorListarPersonas) {
        this.manejadorCrearPersona = manejadorCrearPersona;
        this.manejadorListarPersonas = manejadorListarPersonas;
    }

    @PostMapping
    public Persona crear(@RequestBody Persona persona) {
        return this.manejadorCrearPersona.ejecutar(persona);
    }

    @GetMapping
    public List<Persona> listar() {
        return this.manejadorListarPersonas.ejecutar();
    }
}