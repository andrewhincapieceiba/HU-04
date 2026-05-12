package com.practica.historias.infraestructura.adaptador.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ManejadorError {

    // 1. Atrapa errores de validación manual (cuando ya existe el objeto)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(IllegalArgumentException e) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Atrapa el error del Log: HttpMessageNotReadableException
    // Este ocurre cuando el constructor falla al recibir el JSON de Postman
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarErrorJson(HttpMessageNotReadableException e) {
        Map<String, String> error = new HashMap<>();

        String mensaje = "Error en el formato de los datos";

        // Buscamos tu mensaje personalizado dentro de la "causa" del error técnico
        if (e.getCause() != null && e.getCause().getCause() != null) {
            mensaje = e.getCause().getCause().getMessage();
        } else if (e.getCause() != null) {
            mensaje = e.getCause().getMessage();
        }

        // Limpiamos el mensaje de textos técnicos de Jackson si es necesario
        if (mensaje.contains("problem:")) {
            mensaje = mensaje.substring(mensaje.indexOf("problem:") + 8).trim();
        }

        error.put("mensaje", mensaje);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 3. Error genérico para que el server no "explote" con un 500 feo
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorGenerico(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", "Ocurrió un error inesperado en el servidor");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}