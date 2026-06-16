package com.practica.historias.dominio.modelo;

import com.practica.historias.dominio.excepcion.ExcepcionNegocio;
import java.time.LocalDate;

public class Persona {

    private static final String MENSAJE_NOMBRE_OBLIGATORIO = "El nombre es obligatorio";
    private static final String MENSAJE_APELLIDO_OBLIGATORIO = "El apellido es obligatorio";
    private static final String MENSAJE_EMAIL_INVALIDO = "El correo no tiene un formato válido (ejemplo: usuario@gmail.com)";
    private static final String MENSAJE_FECHA_INVALIDA = "No se pueden poner fechas futuras en el nacimiento";

    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final LocalDate fechaNacimiento;

    public Persona(Long id, String nombre, String apellido, String email, LocalDate fechaNacimiento) {
        validarObligatorio(nombre, MENSAJE_NOMBRE_OBLIGATORIO);
        validarObligatorio(apellido, MENSAJE_APELLIDO_OBLIGATORIO);
        validarFormatoEmail(email);
        validarFechaNacimiento(fechaNacimiento);

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    private void validarObligatorio(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new ExcepcionNegocio(mensaje);
        }
    }

    private void validarFormatoEmail(String email) {
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new ExcepcionNegocio(MENSAJE_EMAIL_INVALIDO);
        }
    }

    private void validarFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento != null && fechaNacimiento.isAfter(LocalDate.now())) {
            throw new ExcepcionNegocio(MENSAJE_FECHA_INVALIDA);
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
}