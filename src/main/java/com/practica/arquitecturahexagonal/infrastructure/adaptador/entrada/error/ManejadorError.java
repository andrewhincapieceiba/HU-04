package com.practica.arquitecturahexagonal.infrastructure.adaptador.entrada.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorError {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarError(Exception ex) {
        Map<String, String> respuesta = new HashMap<>();


        String mensaje = ex.getMessage();
        if (ex.getCause() != null) {
            mensaje = ex.getCause().getMessage();
        }

        if (mensaje != null && mensaje.contains(":")) {
            mensaje = mensaje.substring(mensaje.lastIndexOf(":") + 1).trim();
        }

        respuesta.put("mensaje", mensaje);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}