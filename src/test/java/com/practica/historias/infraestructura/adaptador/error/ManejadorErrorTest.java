package com.practica.historias.infraestructura.adaptador.error;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManejadorErrorTest {

    private final ManejadorError manejadorError = new ManejadorError();

    @Test
    void debeManejarExcepcionNegocio() {
        ExcepcionNegocio excepcion =
                new ExcepcionNegocio("El nombre es obligatorio");

        ResponseEntity<Map<String, String>> respuesta =
                manejadorError.manejarExcepcionNegocio(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        assertEquals(
                "El nombre es obligatorio",
                respuesta.getBody().get("mensaje")
        );
    }

    @Test
    void debeManejarErrorJsonConCausaAnidada() {
        RuntimeException causaReal =
                new RuntimeException("problem: Correo inválido");

        RuntimeException causaIntermedia =
                new RuntimeException(causaReal);

        HttpMessageNotReadableException excepcion =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        causaIntermedia
                );

        ResponseEntity<Map<String, String>> respuesta =
                manejadorError.manejarErrorJson(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        assertEquals(
                "Correo inválido",
                respuesta.getBody().get("mensaje")
        );
    }

    @Test
    void debeManejarErrorJsonConCausaSimple() {
        HttpMessageNotReadableException excepcion =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        new RuntimeException("Fecha inválida")
                );

        ResponseEntity<Map<String, String>> respuesta =
                manejadorError.manejarErrorJson(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        assertEquals(
                "Fecha inválida",
                respuesta.getBody().get("mensaje")
        );
    }

    @Test
    void debeManejarErrorJsonSinCausa() {

        HttpMessageNotReadableException errorJson =
                new HttpMessageNotReadableException("JSON inválido");

        ResponseEntity<Map<String, String>> respuesta =
                manejadorError.manejarErrorJson(errorJson);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                respuesta.getStatusCode()
        );

        assertEquals(
                "Error en el formato de los datos",
                respuesta.getBody().get("mensaje")
        );
    }

    @Test
    void debeManejarErrorJsonConMensajeSinProblem() {

        RuntimeException errorInterno =
                new RuntimeException("Correo incorrecto");

        RuntimeException errorIntermedio =
                new RuntimeException(errorInterno);

        HttpMessageNotReadableException errorJson =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        errorIntermedio
                );

        ResponseEntity<Map<String, String>> respuesta =
                manejadorError.manejarErrorJson(errorJson);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                respuesta.getStatusCode()
        );

        assertEquals(
                "Correo incorrecto",
                respuesta.getBody().get("mensaje")
        );
    }

    @Test
    void debeManejarErrorGenerico() {
        Exception exception =
                new Exception("Error inesperado");

        ResponseEntity<Map<String, String>> respuesta =
                manejadorError.manejarErrorGenerico(exception);

        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                respuesta.getStatusCode()
        );

        Assertions.assertNotNull(respuesta.getBody());
        assertEquals(
                "Ocurrió un error inesperado en el servidor",
                respuesta.getBody().get("mensaje")
        );
    }
    @Test
    void debeManejarErrorJsonCuandoMensajeEsNull() {

        RuntimeException errorInterno =
                new RuntimeException((String) null);

        RuntimeException errorIntermedio =
                new RuntimeException(errorInterno);

        HttpMessageNotReadableException errorJson =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        errorIntermedio
                );

        ResponseEntity<Map<String, String>> respuesta =
                manejadorError.manejarErrorJson(errorJson);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                respuesta.getStatusCode()
        );

        assertEquals(
                null,
                respuesta.getBody().get("mensaje")
        );
    }
}