package com.practica.historias.infraestructura.controlador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.manejador.ManejadorActualizarPersona;
import com.practica.historias.aplicacion.manejador.ManejadorBuscarPersonaPorId;
import com.practica.historias.aplicacion.manejador.ManejadorBuscarPersonasAvanzado;
import com.practica.historias.aplicacion.manejador.ManejadorCrearPersona;
import com.practica.historias.aplicacion.manejador.ManejadorEliminarPersona;
import com.practica.historias.aplicacion.manejador.ManejadorListarPersonas;
import com.practica.historias.dominio.modelo.PaginaResultado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/personas")
@Tag(name = "Personas", description = "Endpoints para el ciclo de vida, diagnósticos y búsquedas de personas")
public class PersonaControlador {

    private final ManejadorCrearPersona manejadorCrearPersona;
    private final ManejadorListarPersonas manejadorListarPersonas;
    private final ManejadorBuscarPersonaPorId manejadorBuscarPersonaPorId;
    private final ManejadorActualizarPersona manejadorActualizarPersona;
    private final ManejadorEliminarPersona manejadorEliminarPersona;
    private final ManejadorBuscarPersonasAvanzado manejadorBuscarPersonasAvanzado;

    public PersonaControlador(
            ManejadorCrearPersona manejadorCrearPersona, 
            ManejadorListarPersonas manejadorListarPersonas, 
            ManejadorBuscarPersonaPorId manejadorBuscarPersonaPorId, 
            ManejadorActualizarPersona manejadorActualizarPersona, 
            ManejadorEliminarPersona manejadorEliminarPersona,
            ManejadorBuscarPersonasAvanzado manejadorBuscarPersonasAvanzado) {
        this.manejadorCrearPersona = manejadorCrearPersona;
        this.manejadorListarPersonas = manejadorListarPersonas;
        this.manejadorBuscarPersonaPorId = manejadorBuscarPersonaPorId;
        this.manejadorActualizarPersona = manejadorActualizarPersona;
        this.manejadorEliminarPersona = manejadorEliminarPersona;
        this.manejadorBuscarPersonasAvanzado = manejadorBuscarPersonasAvanzado;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva persona", description = "Registra una persona en el sistema validando que los datos cumplan con las reglas de negocio y sanitización anti-XSS.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Persona creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error en validaciones"),
        @ApiResponse(responseCode = "415", description = "Media Type no soportado. Debe ser application/json"),
        @ApiResponse(responseCode = "500", description = "Error interno inesperado en el servidor")
    })
    public PersonaDTO crear(@Valid @RequestBody PersonaDTO personaDTO) {
        return this.manejadorCrearPersona.ejecutar(personaDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todas las personas", description = "Devuelve una lista completa de las personas registradas ordenadas ascendentemente por apellido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado procesado con éxito")
    })
    public List<PersonaDTO> listar() {
        return this.manejadorListarPersonas.ejecutar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar persona por su ID", description = "Devuelve los datos de una persona específica registrada en la base de datos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Persona encontrada con éxito"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada con el ID suministrado")
    })
    public PersonaDTO buscarPorId(@PathVariable Long id) {
        return this.manejadorBuscarPersonaPorId.ejecutar(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Búsqueda avanzada con paginación", description = "Permite filtrar personas dinámicamente de forma parcial y 'case-insensitive' por nombre, apellido o rangos de edad.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Búsqueda avanzada procesada con éxito")
    })
    public ResponseEntity<PaginaResultado<PersonaDTO>> buscarAvanzado(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) Integer edadMin,
            @RequestParam(required = false) Integer edadMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "apellido") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        
        com.practica.historias.dominio.modelo.PersonaSearchCriteria criterios = 
                new com.practica.historias.dominio.modelo.PersonaSearchCriteria(nombre, apellido, edadMin, edadMax);
        
        PaginaResultado<PersonaDTO> resultado = this.manejadorBuscarPersonasAvanzado.ejecutar(criterios, page, size, sort, direction);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una persona existente", description = "Modifica las propiedades de un registro existente localizándolo a través de su identificador ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Persona modificada correctamente"),
        @ApiResponse(responseCode = "400", description = "Campos enviados no válidos"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada para actualizar")
    })
    public PersonaDTO actualizar(@PathVariable Long id, @Valid @RequestBody PersonaDTO personaDTO) {
        return this.manejadorActualizarPersona.ejecutar(id, personaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una persona", description = "Borra físicamente el registro del sistema a través de su identificador único ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Persona eliminada exitosamente sin contenido en la respuesta"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada para eliminar")
    })
    public void eliminar(@PathVariable Long id) {
        this.manejadorEliminarPersona.ejecutar(id);
    }

    @GetMapping("/health")
    @Operation(summary = "Verificar estado de salud de la API (HU-02)", description = "Endpoint de diagnóstico rápido para comprobar la disponibilidad operativa básica del servidor.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "API funcionando óptimamente")
    })
    public ResponseEntity<Map<String, String>> verificarSalud() {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("status", "OK");
        respuesta.put("message", "API funcionando");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/welcome/{nombre}")
    @Operation(summary = "Endpoint de bienvenida personalizado (HU-02)", description = "Recibe un parámetro dinámico en la URL y retorna un saludo amigable en formato JSON.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mensaje de saludo renderizado con éxito")
    })
    public ResponseEntity<Map<String, String>> saludoPersonalizado(@PathVariable String nombre) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("message", "¡Bienvenido al sistema, " + nombre + "!");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}