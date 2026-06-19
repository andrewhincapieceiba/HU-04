package com.practica.historias.infraestructura.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import com.practica.historias.infraestructura.adaptador.repositorio.JpaPersonaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PersonaControladorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaPersonaRepositorio jpaPersonaRepositorio;

    private PersonaEntity personaExistente;

    @BeforeEach
    void setUp() {
        this.personaExistente = new PersonaEntity();
        this.personaExistente.setNombre("Andrew");
        this.personaExistente.setApellido("Hincapie");
        this.personaExistente.setEmail("andrew@mail.com");
        this.personaExistente.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        this.personaExistente = jpaPersonaRepositorio.save(this.personaExistente);
    }

    @Test
    void debeCrearPersonaEnH2Exitosamente() throws Exception {
        PersonaDTO nuevoDto = new PersonaDTO(null, "Luis", "Diaz", "lucho@mail.com", LocalDate.of(1997, 1, 13));

        this.mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(nuevoDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Luis"))
                .andExpect(jsonPath("$.email").value("lucho@mail.com"));
    }

    @Test
    void debeRetornarBadRequestCuandoNombreFallaValidacionEnPOST() throws Exception {
        PersonaDTO dtoInvalido = new PersonaDTO(null, "", "Zapata", "juan@mail.com", LocalDate.of(1995, 5, 20));

        this.mockMvc.perform(post("/api/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("El nombre es obligatorio"));
    }

    @Test
    void debeBuscarPersonaPorIdDesdeH2() throws Exception {
        this.mockMvc.perform(get("/api/personas/" + this.personaExistente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(this.personaExistente.getId()))
                .andExpect(jsonPath("$.nombre").value("Andrew"))
                .andExpect(jsonPath("$.apellido").value("Hincapie"));
    }

    @Test
    void debeListarTodasLasPersonasDeH2() throws Exception {
        // Asegura que la respuesta sea 200 OK y que traiga una estructura de lista válida
        this.mockMvc.perform(get("/api/personas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void debeActualizarPersonaEnH2Exitosamente() throws Exception {
        PersonaDTO dtoActualizacion = new PersonaDTO(null, "Andrew", "Hincapie Modificado", "andrew.nuevo@mail.com", LocalDate.of(2000, 1, 1));

        this.mockMvc.perform(put("/api/personas/" + this.personaExistente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(dtoActualizacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido").value("Hincapie Modificado"))
                .andExpect(jsonPath("$.email").value("andrew.nuevo@mail.com"));
    }

    @Test
    void debeEliminarPersonaDeH2Exitosamente() throws Exception {
        // Corregido a isNoContent() porque tu controlador responde correctamente con código HTTP 204
        this.mockMvc.perform(delete("/api/personas/" + this.personaExistente.getId()))
                .andExpect(status().isNoContent());
    }
}