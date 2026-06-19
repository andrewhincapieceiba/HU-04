package com.practica.historias.infraestructura.adaptador.servicio;

import com.practica.historias.dominio.excepcion.ExcepcionRecursoNoEncontrado;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import com.practica.historias.infraestructura.adaptador.mapper.PersonaMapper;
import com.practica.historias.infraestructura.adaptador.repositorio.JpaPersonaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonaAdaptadorServicioTest {

    private JpaPersonaRepositorio jpaPersonaRepositorio;
    private PersonaAdaptadorServicio personaAdaptadorServicio;

    @BeforeEach
    void setUp() {
        this.jpaPersonaRepositorio = Mockito.mock(JpaPersonaRepositorio.class);
        PersonaMapper personaMapper = new PersonaMapper();
        this.personaAdaptadorServicio = new PersonaAdaptadorServicio(jpaPersonaRepositorio, personaMapper);
    }

    @Test
    void debeGuardarPersonaCorrectamente() {
        Persona persona = new Persona(null, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));
        PersonaEntity entidadGuardada = crearEntidad(1L, "Andrew", "Hincapie", "andrew@mail.com", 2000, 1, 1);

        when(this.jpaPersonaRepositorio.save(any(PersonaEntity.class))).thenReturn(entidadGuardada);

        Persona resultado = this.personaAdaptadorServicio.guardar(persona);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Andrew", resultado.getNombre());
        assertEquals("Hincapie", resultado.getApellido());
    }

    @Test
    void debeListarPersonasOrdenadasPorApellido() {
        PersonaEntity personaUno = crearEntidad(1L, "Ana", "Alvarez", "ana@mail.com", 1990, 1, 1);
        PersonaEntity personaDos = crearEntidad(2L, "Juan", "Zapata", "juan@mail.com", 1995, 1, 1);

        when(this.jpaPersonaRepositorio.findAllByOrderByApellidoAsc()).thenReturn(Arrays.asList(personaUno, personaDos));

        List<Persona> resultado = this.personaAdaptadorServicio.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Alvarez", resultado.get(0).getApellido());
        assertEquals("Zapata", resultado.get(1).getApellido());
    }

    @Test
    void debeBuscarPersonaPorIdCorrectamente() {
        PersonaEntity entidad = crearEntidad(1L, "Carlos", "Perez", "carlos@mail.com", 1990, 5, 10);

        when(this.jpaPersonaRepositorio.findById(1L)).thenReturn(Optional.of(entidad));

        Persona resultado = this.personaAdaptadorServicio.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
    }

    @Test
    void debeLanzarExcepcionCuandoPersonaNoExiste() {
        when(this.jpaPersonaRepositorio.findById(99L)).thenReturn(Optional.empty());

        ExcepcionRecursoNoEncontrado excepcion = assertThrows(ExcepcionRecursoNoEncontrado.class, () ->
                this.personaAdaptadorServicio.buscarPorId(99L)
        );

        assertEquals("Persona no encontrada", excepcion.getMessage());
    }

    @Test
    void debeActualizarPersonaCorrectamente() {
        PersonaEntity entidadExistente = crearEntidad(1L, "Andrew", "Hincapie", "andrew@mail.com", 2000, 1, 1);
        Persona personaNueva = new Persona(1L, "Andrew", "Hincapie Actualizado", "andrew.nuevo@mail.com", LocalDate.of(2000, 1, 1));
        PersonaEntity entidadActualizada = crearEntidad(1L, "Andrew", "Hincapie Actualizado", "andrew.nuevo@mail.com", 2000, 1, 1);

        when(this.jpaPersonaRepositorio.findById(1L)).thenReturn(Optional.of(entidadExistente));
        when(this.jpaPersonaRepositorio.save(any(PersonaEntity.class))).thenReturn(entidadActualizada);

        Persona resultado = this.personaAdaptadorServicio.actualizar(1L, personaNueva);

        assertNotNull(resultado);
        assertEquals("Hincapie Actualizado", resultado.getApellido());
        assertEquals("andrew.nuevo@mail.com", resultado.getEmail());
    }

    @Test
    void debeEliminarPersonaCorrectamente() {
        PersonaEntity entidad = crearEntidad(1L, "Andrew", "Hincapie", "andrew@mail.com", 2000, 1, 1);
        when(this.jpaPersonaRepositorio.findById(1L)).thenReturn(Optional.of(entidad));

        this.personaAdaptadorServicio.eliminar(1L);

        verify(this.jpaPersonaRepositorio, times(1)).delete(entidad);
    }

    private PersonaEntity crearEntidad(Long id, String nombre, String apellido, String email, int anio, int mes, int dia) {
        PersonaEntity entidad = new PersonaEntity();
        entidad.setId(id);
        entidad.setNombre(nombre);
        entidad.setApellido(apellido);
        entidad.setEmail(email);
        entidad.setFechaNacimiento(LocalDate.of(anio, mes, dia));
        return entidad;
    }
}