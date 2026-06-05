package com.practica.historias.infraestructura.adaptador.servicio;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;
import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import com.practica.historias.infraestructura.adaptador.mapper.PersonaMapper;
import com.practica.historias.infraestructura.adaptador.repositorio.JpaPersonaRepositorio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaAdaptadorServicio implements PersonaRepositorio {

    private final JpaPersonaRepositorio jpaPersonaRepositorio;
    private final PersonaMapper personaMapper;

    public PersonaAdaptadorServicio(
            JpaPersonaRepositorio jpaPersonaRepositorio,
            PersonaMapper personaMapper) {

        this.jpaPersonaRepositorio = jpaPersonaRepositorio;
        this.personaMapper = personaMapper;
    }

    @Override
    public Persona guardar(Persona persona) {
        PersonaEntity entidad = this.personaMapper.aEntidad(persona);
        return this.personaMapper.aDominio(
                this.jpaPersonaRepositorio.save(entidad)
        );
    }

    @Override
    public List<Persona> listarTodos() {

        List<PersonaEntity> entidades =
                this.jpaPersonaRepositorio.findAllByOrderByApellidoAsc();

        List<Persona> personasDominio = new ArrayList<>();

        for (PersonaEntity entidad : entidades) {
            Persona persona = this.personaMapper.aDominio(entidad);
            personasDominio.add(persona);
        }

        return personasDominio;
    }

    @Override
    public Persona buscarPorId(Long id) {

        PersonaEntity entidad =
                this.jpaPersonaRepositorio.findById(id)
                        .orElseThrow(() ->
                                new ExcepcionNegocio("Persona no encontrada"));

        return this.personaMapper.aDominio(entidad);
    }
}