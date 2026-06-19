package com.practica.historias.dominio.modelo;

public class PersonaSearchCriteria {
    private String nombre;
    private String apellido;
    private Integer edadMin;
    private Integer edadMax;

    public PersonaSearchCriteria(String nombre, String apellido, Integer edadMin, Integer edadMax) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edadMin = edadMin;
        this.edadMax = edadMax;
    }

    // Geters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public Integer getEdadMin() { return edadMin; }
    public Integer getEdadMax() { return edadMax; }
}