package com.practica.arquitecturahexagonal.domain.model;


import java.time.LocalDate;

public class Persona {
    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final LocalDate fechaNacimiento;

    public Persona(Long id, String nombre, String apellido, String email, LocalDate fechaNacimiento) {
        validarObligatorio(nombre, "El nombre es obligatorio");
        validarObligatorio(apellido, "El apellido es obligatorio");
        validarEmail(email);

        validarFechaNoFutura(fechaNacimiento);

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    private void validarFechaNoFutura(LocalDate fecha) {
        if (fecha != null && fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser una fecha futura.");
        }
    }

    private void validarObligatorio(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private void validarEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El formato del email no es válido.");
        }
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
}