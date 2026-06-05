package com.practica.historias.dominio.servicio;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import com.practica.historias.infraestructura.adaptador.mapper.PersonaMapper;
import com.practica.historias.infraestructura.adaptador.repositorio.JpaPersonaRepositorio;
import com.practica.historias.infraestructura.adaptador.servicio.PersonaAdaptadorServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PersonaAdaptadorServicioTest {

    private JpaPersonaRepositorio jpaPersonaRepositorio;
    private PersonaMapper personaMapper;
    private PersonaAdaptadorServicio personaAdaptadorServicio;

    @BeforeEach
    void setUp() {
        this.jpaPersonaRepositorio = Mockito.mock(JpaPersonaRepositorio.class);
        this.personaMapper = new PersonaMapper();

        this.personaAdaptadorServicio =
                new PersonaAdaptadorServicio(
                        jpaPersonaRepositorio,
                        personaMapper
                );
    }

    @Test
    void debeGuardarPersonaCorrectamente() {

        Persona persona = new Persona(
                null,
                "Andrew",
                "Hincapie",
                "andrew@mail.com",
                LocalDate.of(2000, 1, 1)
        );

        PersonaEntity entidadGuardada = new PersonaEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setNombre("Andrew");
        entidadGuardada.setApellido("Hincapie");
        entidadGuardada.setEmail("andrew@mail.com");
        entidadGuardada.setFechaNacimiento(LocalDate.of(2000, 1, 1));

        when(jpaPersonaRepositorio.save(any(PersonaEntity.class)))
                .thenReturn(entidadGuardada);

        Persona resultado =
                personaAdaptadorServicio.guardar(persona);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Andrew", resultado.getNombre());
        assertEquals("Hincapie", resultado.getApellido());
    }

    @Test
    void debeListarPersonasOrdenadasPorApellido() {

        PersonaEntity personaUno = new PersonaEntity();
        personaUno.setId(1L);
        personaUno.setNombre("Ana");
        personaUno.setApellido("Alvarez");
        personaUno.setEmail("ana@mail.com");
        personaUno.setFechaNacimiento(
                LocalDate.of(1990, 1, 1)
        );

        PersonaEntity personaDos = new PersonaEntity();
        personaDos.setId(2L);
        personaDos.setNombre("Juan");
        personaDos.setApellido("Zapata");
        personaDos.setEmail("juan@mail.com");
        personaDos.setFechaNacimiento(
                LocalDate.of(1995, 1, 1)
        );

        when(jpaPersonaRepositorio.findAllByOrderByApellidoAsc())
                .thenReturn(Arrays.asList(personaUno, personaDos));

        List<Persona> resultado =
                personaAdaptadorServicio.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Alvarez",
                resultado.get(0).getApellido());
        assertEquals("Zapata",
                resultado.get(1).getApellido());
    }

    @Test
    void debeBuscarPersonaPorIdCorrectamente() {

        PersonaEntity entidad = new PersonaEntity();
        entidad.setId(1L);
        entidad.setNombre("Carlos");
        entidad.setApellido("Perez");
        entidad.setEmail("carlos@mail.com");
        entidad.setFechaNacimiento(
                LocalDate.of(1990, 5, 10)
        );

        when(jpaPersonaRepositorio.findById(1L))
                .thenReturn(Optional.of(entidad));

        Persona resultado =
                personaAdaptadorServicio.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
    }

    @Test
    void debeLanzarExcepcionCuandoPersonaNoExiste() {

        when(jpaPersonaRepositorio.findById(99L))
                .thenReturn(Optional.empty());

        ExcepcionNegocio excepcion =
                assertThrows(
                        ExcepcionNegocio.class,
                        () -> personaAdaptadorServicio.buscarPorId(99L)
                );

        assertEquals(
                "Persona no encontrada",
                excepcion.getMessage()
        );
    }
}