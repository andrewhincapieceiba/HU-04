package com.practica.historias.dominio.puerto;

import com.practica.historias.dominio.modelo.Persona;
import java.util.List;

// Es una 'interface' porque solo es un contrato: dice QUÉ hay que hacer, pero no CÓMO.
public interface PersonaRepositorio {

    // Promesa de que podremos guardar una persona
    Persona guardar(Persona persona);

    // Promesa de que podremos ver a todos los registrados
    List<Persona> listarTodos();
}