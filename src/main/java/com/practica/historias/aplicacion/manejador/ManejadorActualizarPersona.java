package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioActualizarPersona;
import org.springframework.stereotype.Component;

@Component
public class ManejadorActualizarPersona {

    private final ServicioActualizarPersona servicioActualizarPersona;
    private final FabricaPersona fabricaPersona;

    public ManejadorActualizarPersona(ServicioActualizarPersona servicioActualizarPersona, FabricaPersona fabricaPersona) {
        this.servicioActualizarPersona = servicioActualizarPersona;
        this.fabricaPersona = fabricaPersona;
    }

    public PersonaDTO ejecutar(Long id, PersonaDTO personaDTO) {
        Persona persona = this.fabricaPersona.crear(personaDTO);
        Persona personaActualizada = this.servicioActualizarPersona.ejecutar(id, persona);
        return this.fabricaPersona.crearDTO(personaActualizada);
    }
}