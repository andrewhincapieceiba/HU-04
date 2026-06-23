package com.practica.historias.infraestructura.adaptador.servicio;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import com.practica.historias.dominio.excepcion.ExcepcionRecursoNoEncontrado;
import com.practica.historias.dominio.modelo.PaginaResultado;
import com.practica.historias.dominio.modelo.Persona;
import com.practica.historias.dominio.modelo.PersonaSearchCriteria;
import com.practica.historias.dominio.puerto.PersonaRepositorio;
import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import com.practica.historias.infraestructura.adaptador.mapper.PersonaMapper;
import com.practica.historias.infraestructura.adaptador.repositorio.JpaPersonaRepositorio;
import com.practica.historias.infraestructura.adaptador.repositorio.PersonaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonaAdaptadorServicio implements PersonaRepositorio {

    private static final String MENSAJE_PERSONA_NO_ENCONTRADA = "Persona no encontrada";

    private final JpaPersonaRepositorio jpaPersonaRepositorio;
    private final PersonaMapper personaMapper;

    public PersonaAdaptadorServicio(JpaPersonaRepositorio jpaPersonaRepositorio, PersonaMapper personaMapper) {
        this.jpaPersonaRepositorio = jpaPersonaRepositorio;
        this.personaMapper = personaMapper;
    }

    @Override
    public Persona guardar(Persona persona) {
        PersonaEntity entidad = this.personaMapper.aEntidad(persona);
        PersonaEntity entidadGuardada = this.jpaPersonaRepositorio.save(entidad);
        return this.personaMapper.aDominio(entidadGuardada);
    }

    @Override
    public List<Persona> listarTodos() {
        return this.jpaPersonaRepositorio.findAllByOrderByApellidoAsc()
                .stream()
                .map(entidad -> this.personaMapper.aDominio(entidad))
                .collect(Collectors.toList());
    }

    @Override
    public Persona buscarPorId(Long id) {
        PersonaEntity entidad = this.buscarEntidadPorId(id);
        return this.personaMapper.aDominio(entidad);
    }

    @Override
    public PaginaResultado<Persona> buscarAvanzado(PersonaSearchCriteria criterios, int pagina, int tamano, String ordenarPor, String direccion) {
        Sort sort = direccion.equalsIgnoreCase("desc") ? Sort.by(ordenarPor).descending() : Sort.by(ordenarPor).ascending();
        Pageable pageable = PageRequest.of(pagina, tamano, sort);

        Page<PersonaEntity> resultadoPage = this.jpaPersonaRepositorio.findAll(PersonaSpecification.conCriterios(criterios), pageable);

        List<Persona> listaPersonas = resultadoPage.getContent()
                .stream()
                .map(entidad -> this.personaMapper.aDominio(entidad))
                .collect(Collectors.toList());

        return new PaginaResultado<>(listaPersonas, resultadoPage.getTotalElements(), resultadoPage.getTotalPages(), resultadoPage.getNumber());
    }

    @Override
    public Persona actualizar(Long id, Persona persona) {
        PersonaEntity entidadExistente = this.buscarEntidadPorId(id);

        // 💡 CORRECCIÓN M4: Validar la unicidad del email únicamente si este fue modificado
        if (!entidadExistente.getEmail().equalsIgnoreCase(persona.getEmail())) {
            boolean emailYaExiste = this.jpaPersonaRepositorio.existsByEmail(persona.getEmail());
            if (emailYaExiste) {
                throw new ExcepcionNegocio("El email ya está registrado por otra persona");
            }
        }

        entidadExistente.setNombre(persona.getNombre());
        entidadExistente.setApellido(persona.getApellido());
        entidadExistente.setEmail(persona.getEmail());
        entidadExistente.setFechaNacimiento(persona.getFechaNacimiento());

        PersonaEntity entidadActualizada = this.jpaPersonaRepositorio.save(entidadExistente);
        return this.personaMapper.aDominio(entidadActualizada);
    }

    @Override
    public void eliminar(Long id) {
        PersonaEntity entidad = this.buscarEntidadPorId(id);
        this.jpaPersonaRepositorio.delete(entidad);
    }

    private PersonaEntity buscarEntidadPorId(Long id) {
        return this.jpaPersonaRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionRecursoNoEncontrado(MENSAJE_PERSONA_NO_ENCONTRADA));
    }
}