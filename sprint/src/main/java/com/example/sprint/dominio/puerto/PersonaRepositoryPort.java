package com.sprint.dominio.puerto;

import com.sprint.dominio.modelo.Persona;
import java.util.List;

public interface PersonaRepositoryPort {
    
    Persona guardar(Persona persona);
    
    List<Persona> obtenerTodas();
}