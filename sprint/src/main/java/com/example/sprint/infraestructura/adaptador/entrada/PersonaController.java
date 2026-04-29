package com.sprint.infraestructura.adaptador.entrada;

import com.sprint.aplicacion.servicio.PersonaService;
import com.sprint.dominio.modelo.Persona;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping
    public Persona crear(@RequestBody Persona persona) {
        return personaService.registrarPersona(persona);
    }

    @GetMapping
    public List<Persona> listar() {
        return personaService.listarPersonas();
    }
}