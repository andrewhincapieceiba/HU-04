package com.practica.historias.aplicacion.manejador;

import com.practica.historias.aplicacion.dto.PersonaDTO;
import com.practica.historias.dominio.modelo.PaginaResultado;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.modelo.PersonaSearchCriteria;

import com.practica.historias.dominio.puerto.PersonaRepositorio;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManejadorBuscarPersonasAvanzado {

    private final PersonaRepositorio personaRepositorio;

    public ManejadorBuscarPersonasAvanzado(PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    public PaginaResultado<PersonaDTO> ejecutar(PersonaSearchCriteria criterios, int page, int size, String sort, String direction) {
        PaginaResultado<Persona> resultadoDominio = this.personaRepositorio.buscarAvanzado(criterios, page, size, sort, direction);

        List<PersonaDTO> dtos = resultadoDominio.getContenido().stream()
                .map(p -> new PersonaDTO(p.getId(), p.getNombre(), p.getApellido(), p.getEmail(), p.getFechaNacimiento()))
                .collect(Collectors.toList());

        return new PaginaResultado<>(dtos, resultadoDominio.getTotalElements(), resultadoDominio.getTotalPages(), resultadoDominio.getCurrentPage());
    }
}