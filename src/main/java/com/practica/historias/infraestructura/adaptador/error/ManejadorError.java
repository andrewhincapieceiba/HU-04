package com.practica.historias.infraestructura.adaptador.error;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import com.practica.historias.dominio.excepcion.ExcepcionRecursoNoEncontrado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ManejadorError {

    private static final Logger log = LoggerFactory.getLogger(ManejadorError.class);

    private static final String MENSAJE_ERROR_FORMATO = "Error en el formato de los datos";
    private static final String MENSAJE_ERROR_GENERICO = "Ocurrió un error inesperado en el servidor";
    private static final String CLAVE_MENSAJE = "mensaje";

    @ExceptionHandler(ExcepcionNegocio.class)
    public ResponseEntity<Map<String, String>> manejarExcepcionNegocio(ExcepcionNegocio excepcion) {
        Map<String, String> error = new HashMap<>();
        error.put(CLAVE_MENSAJE, excepcion.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExcepcionRecursoNoEncontrado.class)
    public ResponseEntity<Map<String, String>> manejarExcepcionRecursoNoEncontrado(ExcepcionRecursoNoEncontrado excepcion) {
        Map<String, String> error = new HashMap<>();
        error.put(CLAVE_MENSAJE, excepcion.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresValidacion(MethodArgumentNotValidException excepcion) {
        String mensajeError = excepcion.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(org.springframework.validation.FieldError::getDefaultMessage)
                .findFirst()
                .orElse(MENSAJE_ERROR_FORMATO);

        Map<String, String> error = new HashMap<>();
        error.put(CLAVE_MENSAJE, mensajeError);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarErrorJson(HttpMessageNotReadableException excepcion) {
        String mensaje = excepcion.getMostSpecificCause().getMessage();

        if (mensaje == null || mensaje.isBlank() || mensaje.contains("Cannot deserialize") || mensaje.contains("Unexpected char") || mensaje.contains("JSON")) {
            mensaje = MENSAJE_ERROR_FORMATO;
        }

        return new ResponseEntity<>(
                Collections.singletonMap(CLAVE_MENSAJE, mensaje),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorGenerico(Exception excepcion) {
        // Registro estructurado del error interno en la consola del servidor
        log.error("Error inesperado en el servidor: ", excepcion);

        return new ResponseEntity<>(
                Collections.singletonMap(CLAVE_MENSAJE, MENSAJE_ERROR_GENERICO),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}