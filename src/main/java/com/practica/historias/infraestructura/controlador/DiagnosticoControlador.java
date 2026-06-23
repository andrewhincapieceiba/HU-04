package com.practica.historias.infraestructura.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api") // 💡 CORRECCIÓN M5: La raíz base es /api para mapear las rutas directas de la HU-02
@Tag(name = "Diagnóstico", description = "Endpoints globales de salud y bienvenida para el sistema")
public class DiagnosticoControlador {

    @GetMapping("/health")
    @Operation(summary = "Verificar estado de salud de la API (HU-02)", description = "Retorna el estado operativo actual del servidor.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "API funcionando óptimamente")
    })
    public ResponseEntity<Map<String, String>> verificarSalud() {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("status", "OK");
        respuesta.put("message", "API funcionando");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/welcome/{nombre}")
    @Operation(summary = "Endpoint de bienvenida personalizado (HU-02)", description = "Renderiza un saludo dinámico en formato JSON estructurado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensaje de saludo renderizado con éxito")
    })
    public ResponseEntity<Map<String, String>> saludoPersonalizado(@PathVariable String nombre) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("message", "¡Bienvenido al sistema, " + nombre + "!");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}