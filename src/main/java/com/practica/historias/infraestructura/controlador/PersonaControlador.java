package com.practica.historias.infraestructura.controlador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.manejador.ManejadorBuscarPersonaPorId;
import com.practica.historias.aplicacion.manejador.ManejadorCrearPersona;
import com.practica.historias.aplicacion.manejador.ManejadorListarPersonas;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaControlador {

    private final ManejadorCrearPersona manejadorCrearPersona;
    private final ManejadorListarPersonas manejadorListarPersonas;
    private final ManejadorBuscarPersonaPorId manejadorBuscarPersonaPorId;

    public PersonaControlador(
            ManejadorCrearPersona manejadorCrearPersona,
            ManejadorListarPersonas manejadorListarPersonas,
            ManejadorBuscarPersonaPorId manejadorBuscarPersonaPorId) {

        this.manejadorCrearPersona = manejadorCrearPersona;
        this.manejadorListarPersonas = manejadorListarPersonas;
        this.manejadorBuscarPersonaPorId = manejadorBuscarPersonaPorId;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaDTO crear(@RequestBody PersonaDTO personaDTO) {
        return this.manejadorCrearPersona.ejecutar(personaDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonaDTO> listar() {
        return this.manejadorListarPersonas.ejecutar();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonaDTO buscarPorId(@PathVariable Long id) {
        return this.manejadorBuscarPersonaPorId.ejecutar(id);
    }
}