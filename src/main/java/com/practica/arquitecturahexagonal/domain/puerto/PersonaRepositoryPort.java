package com.practica.arquitecturahexagonal.domain.puerto;

import com.practica.arquitecturahexagonal.domain.model.Persona;
import java.util.List;

public interface PersonaRepositoryPort {
    Persona guardar(Persona persona);
    List<Persona> obtenerTodas();
}