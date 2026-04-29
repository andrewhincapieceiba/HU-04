package com.sprint.infraestructura.configuracion;

import com.sprint.aplicacion.servicio.PersonaService;
import com.sprint.dominio.puerto.PersonaRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PersonaService personaService(PersonaRepositoryPort personaRepositoryPort) {
        return new PersonaService(personaRepositoryPort);
    }
}