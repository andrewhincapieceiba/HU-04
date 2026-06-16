package com.practica.historias.dominio.excepcion;

public class ExcepcionRecursoNoEncontrado extends RuntimeException {
    public ExcepcionRecursoNoEncontrado(String mensaje) {
        super(mensaje);
    }
}
