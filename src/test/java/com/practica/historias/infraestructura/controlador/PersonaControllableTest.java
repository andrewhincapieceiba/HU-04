package com.practica.historias.infraestructura.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.manejador.ManejadorActualizarPersona;
import com.practica.historias.aplicacion.manejador.ManejadorBuscarPersonaPorId;
import com.practica.historias.aplicacion.manejador.ManejadorBuscarPersonasAvanzado;
import com.practica.historias.aplicacion.manejador.ManejadorCrearPersona;
import com.practica.historias.aplicacion.manejador.ManejadorEliminarPersona;
import com.practica.historias.aplicacion.manejador.ManejadorListarPersonas;
import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import com.practica.historias.dominio.excepcion.ExcepcionRecursoNoEncontrado;
import com.practica.historias.infraestructura.adaptador.error.ManejadorError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonaControladorTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private ManejadorCrearPersona manejadorCrearPersona;
    private ManejadorListarPersonas manejadorListarPersonas;
    private ManejadorBuscarPersonaPorId manejadorBuscarPersonaPorId;
    private ManejadorActualizarPersona manejadorActualizarPersona;
    private ManejadorEliminarPersona manejadorEliminarPersona;
    private ManejadorBuscarPersonasAvanzado manejadorBuscarPersonasAvanzado; // 💡 Necesario para compilar el constructor

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());

        this.manejadorCrearPersona = Mockito.mock(ManejadorCrearPersona.class);
        this.manejadorListarPersonas = Mockito.mock(ManejadorListarPersonas.class);
        this.manejadorBuscarPersonaPorId = Mockito.mock(ManejadorBuscarPersonaPorId.class);
        this.manejadorActualizarPersona = Mockito.mock(ManejadorActualizarPersona.class);
        this.manejadorEliminarPersona = Mockito.mock(ManejadorEliminarPersona.class);
        this.manejadorBuscarPersonasAvanzado = Mockito.mock(ManejadorBuscarPersonasAvanzado.class);

        // Se actualiza la instancia pasando el nuevo mock requerido por el controlador real
        PersonaControlador personaControlador = new PersonaControlador(
                this.manejadorCrearPersona,
                this.manejadorListarPersonas,
                this.manejadorBuscarPersonaPorId,
                this.manejadorActualizarPersona,
                this.manejadorEliminarPersona,
                this.manejadorBuscarPersonasAvanzado
        );

        this.mockMvc = MockMvcBuilders.standaloneSetup(personaControlador)
                .setControllerAdvice(new ManejadorError())
                .build();
    }

    @Test
    void debeCrearPersonaExitosamente() throws Exception {
        PersonaDTO dtoEntrada = new PersonaDTO(null, "Luis", "Diaz", "lucho@mail.com", LocalDate.of(1997, 1, 13));
        PersonaDTO dtoSalida = new PersonaDTO(1L, "Luis", "Diaz", "lucho@mail.com", LocalDate.of(1997, 1, 13));

        when(this.manejadorCrearPersona.ejecutar(any(PersonaDTO.class))).thenReturn(dtoSalida);

        this.mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Luis"));
    }

    @Test
    void debeRetornarBadRequestCuandoLasValidacionesFallan() throws Exception {
        PersonaDTO dtoInvalido = new PersonaDTO(null, "", "Diaz", "lucho@mail.com", LocalDate.of(1997, 1, 13));

        this.mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("El nombre es obligatorio"));
    }

    @Test
    void debeRetornarListaVaciaCuandoNoHayPersonas() throws Exception {
        when(this.manejadorListarPersonas.ejecutar()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/api/personas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void debeRetornarListaDePersonasRegistradas() throws Exception {
        List<PersonaDTO> lista = Arrays.asList(
                new PersonaDTO(1L, "Luis", "Alvarez", "lucho@mail.com", LocalDate.of(1997, 1, 13)),
                new PersonaDTO(2L, "Juan", "Zapata", "juan@mail.com", LocalDate.of(1995, 5, 20))
        );

        when(this.manejadorListarPersonas.ejecutar()).thenReturn(lista);

        this.mockMvc.perform(get("/api/personas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].apellido").value("Alvarez"))
                .andExpect(jsonPath("$[1].apellido").value("Zapata"));
    }

    @Test
    void debeBuscarPersonaPorId() throws Exception {
        PersonaDTO persona = new PersonaDTO(1L, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        when(this.manejadorBuscarPersonaPorId.ejecutar(1L)).thenReturn(persona);

        this.mockMvc.perform(get("/api/personas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Andrew"))
                .andExpect(jsonPath("$.apellido").value("Hincapie"));
    }

    @Test
    void debeRetornarErrorCuandoPersonaNoExiste() throws Exception {
        // 💡 CORRECCIÓN M1: Se simula la excepcion real y se aserta un 404 (isNotFound) en lugar de un 400
        when(this.manejadorBuscarPersonaPorId.ejecutar(99L))
                .thenThrow(new ExcepcionRecursoNoEncontrado("Persona no encontrada"));

        this.mockMvc.perform(get("/api/personas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("Persona no encontrada"));
    }

    @Test
    void debeActualizarPersonaCorrectamente() throws Exception {
        PersonaDTO entrada = new PersonaDTO(null, "Andrew", "Hincapie Actualizado", "andrew.actualizado@mail.com", LocalDate.of(2000, 1, 1));
        PersonaDTO salida = new PersonaDTO(1L, "Andrew", "Hincapie Actualizado", "andrew.actualizado@mail.com", LocalDate.of(2000, 1, 1));

        when(this.manejadorActualizarPersona.ejecutar(any(Long.class), any(PersonaDTO.class)))
                .thenReturn(salida);

        this.mockMvc.perform(put("/api/personas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.apellido").value("Hincapie Actualizado"));
    }

    @Test
    void debeRetornarBadRequestCuandoActualizaPersonaInexistente() throws Exception {
        PersonaDTO entrada = new PersonaDTO(null, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        when(this.manejadorActualizarPersona.ejecutar(any(Long.class), any(PersonaDTO.class)))
                .thenThrow(new ExcepcionNegocio("Persona no encontrada"));

        this.mockMvc.perform(put("/api/personas/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("Persona no encontrada"));
    }
}