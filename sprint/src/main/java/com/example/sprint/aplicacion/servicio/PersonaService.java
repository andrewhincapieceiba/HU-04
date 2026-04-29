package com.sprint.aplicacion.servicio;

import com.sprint.dominio.modelo.Persona;
import com.sprint.dominio.puerto.PersonaRepositoryPort;
import java.util.List;

public class PersonaService {


    private final PersonaRepositoryPort personaRepositoryPort;

    public PersonaService(PersonaRepositoryPort personaRepositoryPort) {
        this.personaRepositoryPort = personaRepositoryPort;
    }

    public Persona registrarPersona(Persona persona) {

        return personaRepositoryPort.guardar(persona);
    }

    public List<Persona> listarPersonas() {
        return personaRepositoryPort.obtenerTodas();
    }
}