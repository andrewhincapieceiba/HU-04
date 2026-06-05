package com.practica.historias.infraestructura.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.manejador.ManejadorBuscarPersonaPorId;
import com.practica.historias.aplicacion.manejador.ManejadorCrearPersona;
import com.practica.historias.aplicacion.manejador.ManejadorListarPersonas;
import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonaControlador.class)
class PersonaControllableTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ManejadorCrearPersona manejadorCrearPersona;

    @MockBean
    private ManejadorListarPersonas manejadorListarPersonas;

    @MockBean
    private ManejadorBuscarPersonaPorId manejadorBuscarPersonaPorId;

    @Test
    void debeCrearPersonaExitosamente() throws Exception {

        PersonaDTO dtoEntrada = new PersonaDTO(
                null,
                "Luis",
                "Diaz",
                "lucho@mail.com",
                LocalDate.of(1997, 1, 13)
        );

        PersonaDTO dtoSalida = new PersonaDTO(
                1L,
                "Luis",
                "Diaz",
                "lucho@mail.com",
                LocalDate.of(1997, 1, 13)
        );

        when(this.manejadorCrearPersona.ejecutar(any(PersonaDTO.class)))
                .thenReturn(dtoSalida);

        this.mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Luis"));
    }

    @Test
    void debeRetornarBadRequestCuandoLasValidacionesFallan() throws Exception {

        PersonaDTO dtoInvalido = new PersonaDTO(
                null,
                "",
                "Diaz",
                "correoInvalido",
                LocalDate.now().plusDays(1)
        );

        when(this.manejadorCrearPersona.ejecutar(any(PersonaDTO.class)))
                .thenThrow(new ExcepcionNegocio("Validacion fallida de datos"));

        this.mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje")
                        .value("Validacion fallida de datos"));
    }

    @Test
    void debeRetornarListaVaciaCuandoNoHayPersonas() throws Exception {

        when(this.manejadorListarPersonas.ejecutar())
                .thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void debeRetornarListaDePersonasRegistradas() throws Exception {

        List<PersonaDTO> lista = Arrays.asList(
                new PersonaDTO(
                        1L,
                        "Luis",
                        "Alvarez",
                        "lucho@mail.com",
                        LocalDate.of(1997, 1, 13)
                ),
                new PersonaDTO(
                        2L,
                        "Juan",
                        "Zapata",
                        "juan@mail.com",
                        LocalDate.of(1995, 5, 20)
                )
        );

        when(this.manejadorListarPersonas.ejecutar())
                .thenReturn(lista);

        this.mockMvc.perform(get("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].apellido").value("Alvarez"))
                .andExpect(jsonPath("$[1].apellido").value("Zapata"));
    }

    @Test
    void debeBuscarPersonaPorId() throws Exception {

        PersonaDTO persona = new PersonaDTO(
                1L,
                "Andrew",
                "Hincapie",
                "andrew@mail.com",
                LocalDate.of(2000, 1, 1)
        );

        when(this.manejadorBuscarPersonaPorId.ejecutar(1L))
                .thenReturn(persona);

        this.mockMvc.perform(get("/api/personas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Andrew"))
                .andExpect(jsonPath("$.apellido").value("Hincapie"));
    }

    @Test
    void debeRetornarErrorCuandoPersonaNoExiste() throws Exception {

        when(this.manejadorBuscarPersonaPorId.ejecutar(99L))
                .thenThrow(new ExcepcionNegocio("Persona no encontrada"));

        this.mockMvc.perform(get("/api/personas/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje")
                        .value("Persona no encontrada"));
    }
}