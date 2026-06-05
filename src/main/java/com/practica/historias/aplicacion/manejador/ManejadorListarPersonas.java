package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioListarPersonas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ManejadorListarPersonas {

    private final ServicioListarPersonas servicioListarPersonas;
    private final FabricaPersona fabricaPersona;

    public ManejadorListarPersonas(
            ServicioListarPersonas servicioListarPersonas,
            FabricaPersona fabricaPersona) {

        this.servicioListarPersonas = servicioListarPersonas;
        this.fabricaPersona = fabricaPersona;
    }

    public List<PersonaDTO> ejecutar() {

        List<Persona> personasDominio =
                this.servicioListarPersonas.ejecutar();

        List<PersonaDTO> listaDtos = new ArrayList<>();

        for (Persona persona : personasDominio) {
            listaDtos.add(
                    this.fabricaPersona.crearDTO(persona)
            );
        }

        return listaDtos;
    }
}