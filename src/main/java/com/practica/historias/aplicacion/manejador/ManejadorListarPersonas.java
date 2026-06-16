package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.servicio.ServicioListarPersonas;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManejadorListarPersonas {

    private final ServicioListarPersonas servicioListarPersonas;
    private final FabricaPersona fabricaPersona;

    public ManejadorListarPersonas(ServicioListarPersonas servicioListarPersonas, FabricaPersona fabricaPersona) {
        this.servicioListarPersonas = servicioListarPersonas;
        this.fabricaPersona = fabricaPersona;
    }

    public List<PersonaDTO> ejecutar() {
        return this.servicioListarPersonas.ejecutar()
                .stream()
                .map(persona -> this.fabricaPersona.crearDTO(persona))
                .collect(Collectors.toList());
    }
}