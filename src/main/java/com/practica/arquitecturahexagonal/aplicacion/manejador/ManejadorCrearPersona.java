package com.practica.arquitecturahexagonal.aplicacion.manejador;

import com.practica.arquitecturahexagonal.domain.model.Persona;
import com.practica.arquitecturahexagonal.domain.puerto.PersonaRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ManejadorCrearPersona {
    private final PersonaRepositoryPort personaRepositoryPort;

    public ManejadorCrearPersona(PersonaRepositoryPort personaRepositoryPort) {
        this.personaRepositoryPort = personaRepositoryPort;
    }

    public Persona ejecutar(Persona persona) {
        return this.personaRepositoryPort.guardar(persona);
    }
}