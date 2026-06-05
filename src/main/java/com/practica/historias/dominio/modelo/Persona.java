package com.practica.historias.dominio.modelo;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import java.time.LocalDate;

public class Persona {
    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final LocalDate fechaNacimiento;

    public Persona(Long id, String nombre, String apellido, String email, LocalDate fechaNacimiento) {
        // Llamado a los métodos de validación
        validarObligatorio(nombre, "El nombre es obligatorio");
        validarObligatorio(apellido, "El apellido es obligatorio");
        validarFormatoEmail(email);
        validarFechaNacimiento(fechaNacimiento);

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    // --- Métodos de Validación Privados ---

    private void validarObligatorio(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new ExcepcionNegocio(mensaje);
        }
    }

    private void validarFormatoEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new ExcepcionNegocio("El correo no tiene un formato válido (ejemplo: usuario@gmail.com)");
        }
    }

    private void validarFechaNacimiento(LocalDate fecha) {
        if (fecha != null && fecha.isAfter(LocalDate.now())) {
            throw new ExcepcionNegocio("No se pueden poner fechas futuras en el nacimiento");
        }
    }

    // --- Getters ---
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
}