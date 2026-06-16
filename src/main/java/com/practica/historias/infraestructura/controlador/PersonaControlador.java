package com.practica.historias.infraestructura.controlador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.manejador.ManejadorActualizarPersona;
import com.practica.historias.aplicacion.manejador.ManejadorBuscarPersonaPorId;
import com.practica.historias.aplicacion.manejador.ManejadorCrearPersona;
import com.practica.historias.aplicacion.manejador.ManejadorEliminarPersona;
import com.practica.historias.aplicacion.manejador.ManejadorListarPersonas;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaControlador {

    private final ManejadorCrearPersona manejadorCrearPersona;
    private final ManejadorListarPersonas manejadorListarPersonas;
    private final ManejadorBuscarPersonaPorId manejadorBuscarPersonaPorId;
    private final ManejadorActualizarPersona manejadorActualizarPersona;
    private final ManejadorEliminarPersona manejadorEliminarPersona;

    public PersonaControlador(ManejadorCrearPersona manejadorCrearPersona, ManejadorListarPersonas manejadorListarPersonas, ManejadorBuscarPersonaPorId manejadorBuscarPersonaPorId, ManejadorActualizarPersona manejadorActualizarPersona, ManejadorEliminarPersona manejadorEliminarPersona) {
        this.manejadorCrearPersona = manejadorCrearPersona;
        this.manejadorListarPersonas = manejadorListarPersonas;
        this.manejadorBuscarPersonaPorId = manejadorBuscarPersonaPorId;
        this.manejadorActualizarPersona = manejadorActualizarPersona;
        this.manejadorEliminarPersona = manejadorEliminarPersona;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonaDTO crear(@Valid @RequestBody PersonaDTO personaDTO) {
        return this.manejadorCrearPersona.ejecutar(personaDTO);
    }

    @GetMapping
    public List<PersonaDTO> listar() {
        return this.manejadorListarPersonas.ejecutar();
    }

    @GetMapping("/{id}")
    public PersonaDTO buscarPorId(@PathVariable Long id) {
        return this.manejadorBuscarPersonaPorId.ejecutar(id);
    }

    @PutMapping("/{id}")
    public PersonaDTO actualizar(@PathVariable Long id, @Valid @RequestBody PersonaDTO personaDTO) {
        return this.manejadorActualizarPersona.ejecutar(id, personaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.manejadorEliminarPersona.ejecutar(id);
    }
}