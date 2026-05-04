package com.practica.arquitecturahexagonal.aplicacion.manejador;

import com.practica.arquitecturahexagonal.domain.model.Persona;
import com.practica.arquitecturahexagonal.domain.puerto.PersonaRepositoryPort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManejadorListarPersonas {
    private final PersonaRepositoryPort personaRepositoryPort;

    public ManejadorListarPersonas(PersonaRepositoryPort personaRepositoryPort) {
        this.personaRepositoryPort = personaRepositoryPort;
    }

    public List<Persona> ejecutar() {
        return this.personaRepositoryPort.obtenerTodas();
    }
}