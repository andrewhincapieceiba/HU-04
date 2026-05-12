package com.practica.historias.infraestructura.configuracion;

import com.practica.historias.dominio.puerto.PersonaRepositorio;
import com.practica.historias.dominio.servicio.ServicioCrearPersona;
import com.practica.historias.aplicacion.manejador.ManejadorCrearPersona;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicioPersona {

    @Bean
    public ServicioCrearPersona servicioCrearPersona(PersonaRepositorio personaRepositorio) {
        return new ServicioCrearPersona(personaRepositorio);
    }

    @Bean
    public ManejadorCrearPersona manejadorCrearPersona(ServicioCrearPersona servicioCrearPersona) {
        return new ManejadorCrearPersona(servicioCrearPersona);
    }
}