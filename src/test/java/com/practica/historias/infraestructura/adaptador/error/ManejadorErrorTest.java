package com.practica.historias.infraestructura.adaptador.error;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManejadorErrorTest {

    private final ManejadorError manejadorError = new ManejadorError();

    @Test
    void debeManejarExcepcionNegocio() {
        ExcepcionNegocio excepcion = new ExcepcionNegocio("El nombre es obligatorio");

        ResponseEntity<Map<String, String>> respuesta =
                this.manejadorError.manejarExcepcionNegocio(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("El nombre es obligatorio", respuesta.getBody().get("mensaje"));
    }

    @Test
    void debeManejarErrorJsonConCausaAnidada() {
        RuntimeException causaReal = new RuntimeException("problem: Correo inválido");
        RuntimeException causaIntermedia = new RuntimeException(causaReal);

        MockHttpInputMessage inputMessage =
                new MockHttpInputMessage("{}".getBytes(StandardCharsets.UTF_8));

        HttpMessageNotReadableException excepcion =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        causaIntermedia,
                        inputMessage
                );

        ResponseEntity<Map<String, String>> respuesta =
                this.manejadorError.manejarErrorJson(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody().get("mensaje"));
    }

    @Test
    void debeManejarErrorJsonConCausaSimple() {
        MockHttpInputMessage inputMessage =
                new MockHttpInputMessage("{}".getBytes(StandardCharsets.UTF_8));

        HttpMessageNotReadableException excepcion =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        new RuntimeException("Fecha inválida"),
                        inputMessage
                );

        ResponseEntity<Map<String, String>> respuesta =
                this.manejadorError.manejarErrorJson(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("Fecha inválida", respuesta.getBody().get("mensaje"));
    }

    @Test
    void debeManejarErrorJsonSinCausa() {
        MockHttpInputMessage inputMessage =
                new MockHttpInputMessage("{}".getBytes(StandardCharsets.UTF_8));

        HttpMessageNotReadableException errorJson =
                new HttpMessageNotReadableException(
                        "JSON inválido",
                        (Throwable) null,
                        inputMessage
                );

        ResponseEntity<Map<String, String>> respuesta =
                this.manejadorError.manejarErrorJson(errorJson);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        // Actualizado para coincidir con el comportamiento de ManejadorError
        assertEquals(
                "Error en el formato de los datos",
                respuesta.getBody().get("mensaje")
        );
    }

    @Test
    void debeManejarErrorJsonConMensajeSinProblem() {
        RuntimeException errorInterno = new RuntimeException("Correo incorrecto");
        RuntimeException errorIntermedio = new RuntimeException(errorInterno);

        MockHttpInputMessage inputMessage =
                new MockHttpInputMessage("{}".getBytes(StandardCharsets.UTF_8));

        HttpMessageNotReadableException errorJson =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        errorIntermedio,
                        inputMessage
                );

        ResponseEntity<Map<String, String>> respuesta =
                this.manejadorError.manejarErrorJson(errorJson);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("Correo incorrecto", respuesta.getBody().get("mensaje"));
    }

    @Test
    void debeManejarErrorGenerico() {
        Exception exception = new Exception("Error inesperado");

        ResponseEntity<Map<String, String>> respuesta =
                this.manejadorError.manejarErrorGenerico(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(
                "Ocurrió un error inesperado en el servidor",
                respuesta.getBody().get("mensaje")
        );
    }

    @Test
    void debeManejarErrorJsonCuandoMensajeEsNull() {
        RuntimeException errorInterno = new RuntimeException((String) null);
        RuntimeException errorIntermedio = new RuntimeException(errorInterno);

        MockHttpInputMessage inputMessage =
                new MockHttpInputMessage("{}".getBytes(StandardCharsets.UTF_8));

        HttpMessageNotReadableException errorJson =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        errorIntermedio,
                        inputMessage
                );

        ResponseEntity<Map<String, String>> respuesta =
                this.manejadorError.manejarErrorJson(errorJson);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals(
                "Error en el formato de los datos",
                respuesta.getBody().get("mensaje")
        );
    }

    @Test
    void debeManejarErrorJsonConCausaAnidadaSinPrefijoProblem() {
        RuntimeException causaReal =
                new RuntimeException("Error simple de parseo");

        RuntimeException causaIntermedia =
                new RuntimeException(causaReal);

        MockHttpInputMessage inputMessage =
                new MockHttpInputMessage(
                        "{}".getBytes(StandardCharsets.UTF_8)
                );

        HttpMessageNotReadableException excepcion =
                new HttpMessageNotReadableException(
                        "Error JSON",
                        causaIntermedia,
                        inputMessage
                );

        ResponseEntity<Map<String, String>> respuesta =
                this.manejadorError.manejarErrorJson(excepcion);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals(
                "Error simple de parseo",
                respuesta.getBody().get("mensaje")
        );
    }
}
