package com.practica.historias.infraestructura.adaptador.error;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ManejadorError {

    // 1. Atrapa nuestra excepción personalizada de Dominio
    @ExceptionHandler(ExcepcionNegocio.class)
    public ResponseEntity<Map<String, String>> manejarExcepcionNegocio(ExcepcionNegocio e) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Atrapa el error cuando Jackson falla al mapear el JSON (Constructor de Persona)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarErrorJson(HttpMessageNotReadableException e) {
        Map<String, String> error = new HashMap<>();
        String mensaje = "Error en el formato de los datos";

        // Extraemos el mensaje de ExcepcionNegocio que Jackson envolvió
        if (e.getCause() != null && e.getCause().getCause() != null) {
            mensaje = e.getCause().getCause().getMessage();
        } else if (e.getCause() != null) {
            mensaje = e.getCause().getMessage();
        }

        // Limpieza de ruido técnico de Jackson
        if (mensaje != null && mensaje.contains("problem:")) {
            mensaje = mensaje.substring(mensaje.indexOf("problem:") + 8).trim();
        }

        error.put("mensaje", mensaje);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 3. Error genérico para estabilidad del sistema
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorGenerico(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", "Ocurrió un error inesperado en el servidor");
        // Opcional: imprimir el log para que tú como Andrew veas qué rompió
        // e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}