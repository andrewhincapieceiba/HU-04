package com.practica.historias.dominio.puerto;

import com.practica.historias.dominio.modelo.Persona;
import java.util.List;

public interface PersonaRepositorio {

    Persona guardar(Persona persona);

    List<Persona> listarTodos();

    Persona buscarPorId(Long id);

    Persona actualizar(Long id, Persona persona);

    void eliminar(Long id);
}