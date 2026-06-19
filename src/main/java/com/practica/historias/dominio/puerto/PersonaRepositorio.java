package com.practica.historias.dominio.puerto;

import com.practica.historias.dominio.modelo.PaginaResultado;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.modelo.PersonaSearchCriteria;

import java.util.List;

public interface PersonaRepositorio {

    Persona guardar(Persona persona);

    List<Persona> listarTodos();

    Persona buscarPorId(Long id);

    Persona actualizar(Long id, Persona persona);

    void eliminar(Long id);

    PaginaResultado<Persona> buscarAvanzado(PersonaSearchCriteria criterios, int page, int size, String sort, String direction);
}