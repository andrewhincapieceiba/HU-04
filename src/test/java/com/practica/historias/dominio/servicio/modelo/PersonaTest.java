package com.practica.historias.dominio.servicio.modelo;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import com.practica.historias.dominio.modelo.Persona;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonaTest {

    @Test
    void debeCrearPersonaCorrectamente() {
        Persona persona = new Persona(1L, "Andrew", "Hincapie", "andrew@mail.com", LocalDate.of(2000, 1, 1));

        assertEquals(1L, persona.getId());
        assertEquals("Andrew", persona.getNombre());
        assertEquals("Hincapie", persona.getApellido());
        assertEquals("andrew@mail.com", persona.getEmail());
    }

    @Test
    void debeLanzarErrorCuandoNombreEsNull() {
        ExcepcionNegocio ex = assertThrows(ExcepcionNegocio.class, () ->
                new Persona(1L, null, "Perez", "correo@mail.com", LocalDate.of(2000, 1, 1))
        );
        assertEquals("El nombre es obligatorio", ex.getMessage());
    }

    @Test
    void debeLanzarErrorCuandoNombreEsVacio() {
        ExcepcionNegocio ex = assertThrows(ExcepcionNegocio.class, () ->
                new Persona(1L, "", "Perez", "correo@mail.com", LocalDate.of(2000, 1, 1))
        );
        assertEquals("El nombre es obligatorio", ex.getMessage());
    }

    @Test
    void debeLanzarErrorCuandoApellidoEsNull() {
        ExcepcionNegocio ex = assertThrows(ExcepcionNegocio.class, () ->
                new Persona(1L, "Andrew", null, "correo@mail.com", LocalDate.of(2000, 1, 1))
        );
        assertEquals("El apellido es obligatorio", ex.getMessage());
    }

    @Test
    void debeLanzarErrorCuandoApellidoEsVacio() {
        ExcepcionNegocio ex = assertThrows(ExcepcionNegocio.class, () ->
                new Persona(1L, "Andrew", "", "correo@mail.com", LocalDate.of(2000, 1, 1))
        );
        assertEquals("El apellido es obligatorio", ex.getMessage());
    }

    @Test
    void debeLanzarErrorCuandoEmailEsNull() {
        ExcepcionNegocio ex = assertThrows(ExcepcionNegocio.class, () ->
                new Persona(1L, "Andrew", "Perez", null, LocalDate.of(2000, 1, 1))
        );
        assertEquals("El correo no tiene un formato válido (ejemplo: usuario@gmail.com)", ex.getMessage());
    }

    @Test
    void debeLanzarErrorCuandoEmailNoTieneArroba() {
        ExcepcionNegocio ex = assertThrows(ExcepcionNegocio.class, () ->
                new Persona(1L, "Andrew", "Perez", "correomail.com", LocalDate.of(2000, 1, 1))
        );
        assertEquals("El correo no tiene un formato válido (ejemplo: usuario@gmail.com)", ex.getMessage());
    }

    @Test
    void debeLanzarErrorCuandoEmailNoTienePunto() {
        ExcepcionNegocio ex = assertThrows(ExcepcionNegocio.class, () ->
                new Persona(1L, "Andrew", "Perez", "correo@mailcom", LocalDate.of(2000, 1, 1))
        );
        assertEquals("El correo no tiene un formato válido (ejemplo: usuario@gmail.com)", ex.getMessage());
    }

    @Test
    void debeLanzarErrorCuandoFechaEsFutura() {
        ExcepcionNegocio ex = assertThrows(ExcepcionNegocio.class, () ->
                new Persona(1L, "Andrew", "Perez", "correo@mail.com", LocalDate.now().plusDays(1))
        );
        assertEquals("No se pueden poner fechas futuras en el nacimiento", ex.getMessage());
    }

    @Test
    void debePermitirFechaNull() {
        Persona persona = new Persona(1L, "Andrew", "Perez", "correo@mail.com", null);
        assertNotNull(persona);
        assertNull(persona.getFechaNacimiento());
    }
}