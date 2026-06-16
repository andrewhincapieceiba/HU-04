package com.practica.historias.infraestructura.configuracion;

import com.practica.historias.dominio.puerto.PersonaRepositorio;
import com.practica.historias.dominio.servicio.ServicioActualizarPersona;
import com.practica.historias.dominio.servicio.ServicioBuscarPersonaPorId;
import com.practica.historias.dominio.servicio.ServicioCrearPersona;
import com.practica.historias.dominio.servicio.ServicioListarPersonas;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.practica.historias.dominio.servicio.ServicioEliminarPersona;

@Configuration
public class BeanServicioPersona {

    @Bean
    public ServicioCrearPersona servicioCrearPersona(
            PersonaRepositorio personaRepositorio) {

        return new ServicioCrearPersona(personaRepositorio);
    }

    @Bean
    public ServicioListarPersonas servicioListarPersonas(
            PersonaRepositorio personaRepositorio) {

        return new ServicioListarPersonas(personaRepositorio);
    }

    @Bean
    public ServicioBuscarPersonaPorId servicioBuscarPersonaPorId(
            PersonaRepositorio personaRepositorio) {

        return new ServicioBuscarPersonaPorId(personaRepositorio);
    }
    @Bean
    public ServicioActualizarPersona servicioActualizarPersona(
            PersonaRepositorio personaRepositorio) {

        return new ServicioActualizarPersona(personaRepositorio);
    }

    @Bean
    public ServicioEliminarPersona servicioEliminarPersona(
            PersonaRepositorio personaRepositorio) {

        return new ServicioEliminarPersona(personaRepositorio);
    }


}