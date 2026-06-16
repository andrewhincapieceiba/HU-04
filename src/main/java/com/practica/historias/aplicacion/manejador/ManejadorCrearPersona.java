package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioCrearPersona;
import org.springframework.stereotype.Component;

@Component
public class ManejadorCrearPersona {

    private final ServicioCrearPersona servicioCrearPersona;
    private final FabricaPersona fabricaPersona;

    public ManejadorCrearPersona(ServicioCrearPersona servicioCrearPersona, FabricaPersona fabricaPersona) {
        this.servicioCrearPersona = servicioCrearPersona;
        this.fabricaPersona = fabricaPersona;
    }

    public PersonaDTO ejecutar(PersonaDTO personaDTO) {
        Persona personaDominio = this.fabricaPersona.crear(personaDTO);
        Persona personaCreada = this.servicioCrearPersona.ejecutar(personaDominio);
        return this.fabricaPersona.crearDTO(personaCreada);
    }
}