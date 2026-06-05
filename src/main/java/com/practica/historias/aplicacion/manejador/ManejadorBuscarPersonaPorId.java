package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.aplicacion.fabrica.FabricaPersona;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.servicio.ServicioBuscarPersonaPorId;
import org.springframework.stereotype.Component;

@Component
public class ManejadorBuscarPersonaPorId {

    private final ServicioBuscarPersonaPorId servicioBuscarPersonaPorId;
    private final FabricaPersona fabricaPersona;

    public ManejadorBuscarPersonaPorId(
            ServicioBuscarPersonaPorId servicioBuscarPersonaPorId,
            FabricaPersona fabricaPersona) {

        this.servicioBuscarPersonaPorId = servicioBuscarPersonaPorId;
        this.fabricaPersona = fabricaPersona;
    }

    public PersonaDTO ejecutar(Long id) {

        Persona persona =
                this.servicioBuscarPersonaPorId.ejecutar(id);

        return this.fabricaPersona.crearDTO(persona);
    }
}