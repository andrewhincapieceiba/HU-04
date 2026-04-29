package com.sprint.infraestructura.adaptador.salida;

import com.sprint.dominio.modelo.Persona;
import com.sprint.dominio.puerto.PersonaRepositoryPort;
import com.sprint.infraestructura.adaptador.repositorio.JpaPersonaRepository;
import com.sprint.infraestructura.entidad.PersonaEntity;
import com.sprint.infraestructura.mapper.PersonaMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonaPersistenceAdapter implements PersonaRepositoryPort {

    private final JpaPersonaRepository repository;
    private final PersonaMapper mapper;

    public PersonaPersistenceAdapter(JpaPersonaRepository repository, PersonaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Persona guardar(Persona persona) {
        PersonaEntity entity = mapper.toEntity(persona);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Persona> obtenerTodas() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}