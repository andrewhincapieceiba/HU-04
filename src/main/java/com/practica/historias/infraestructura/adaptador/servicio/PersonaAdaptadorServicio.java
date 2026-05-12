package com.practica.historias.infraestructura.adaptador.servicio;

import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.puerto.PersonaRepositorio;
import com.practica.historias.infraestructura.adaptador.repositorio.JpaPersonaRepositorio;
import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity; // LA NUEVA RUTA
import com.practica.historias.infraestructura.adaptador.mapper.PersonaMapper;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PersonaAdaptadorServicio implements PersonaRepositorio {

    private final JpaPersonaRepositorio jpaRepositorio;
    private final PersonaMapper personaMapper;

    public PersonaAdaptadorServicio(JpaPersonaRepositorio jpaRepositorio, PersonaMapper personaMapper) {
        this.jpaRepositorio = jpaRepositorio;
        this.personaMapper = personaMapper;
    }

    @Override
    public Persona guardar(Persona persona) {
        PersonaEntity entidad = personaMapper.aEntidad(persona);
        return personaMapper.aDominio(jpaRepositorio.save(entidad));
    }

    @Override
    public List<Persona> listarTodos() {
        return jpaRepositorio.findAll().stream()
                .map(personaMapper::aDominio)
                .toList();
    }
}