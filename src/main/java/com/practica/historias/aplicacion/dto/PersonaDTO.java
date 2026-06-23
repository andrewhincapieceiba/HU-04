package com.practica.historias.aplicacion.dto;

import com.practica.historias.infraestructura.adaptador.validacion.Sanitized;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Schema(description = "Modelo de transferencia de datos para representar una Persona")
public class PersonaDTO {

    @Schema(description = "Identificador único autoincremental de la persona", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Sanitized(message = "El nombre contiene caracteres o scripts no permitidos")
    @Schema(description = "Nombre de la persona", example = "Luis", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Sanitized(message = "El apellido contiene caracteres o scripts no permitidos")
    @Schema(description = "Apellido de la persona", example = "Diaz", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellido;

    @NotBlank(message = "El correo no tiene un formato válido (ejemplo: usuario@gmail.com)")
    @Email(message = "El correo no tiene un formato válido (ejemplo: usuario@gmail.com)")
    @Schema(description = "Correo electrónico de contacto", example = "lucho@mail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @PastOrPresent(message = "No se pueden poner fechas futuras en el nacimiento")
    @Schema(description = "Fecha de nacimiento en formato YYYY-MM-DD", example = "1997-01-13")
    private LocalDate fechaNacimiento;

    public PersonaDTO() {}

    public PersonaDTO(Long id, String nombre, String apellido, String email, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}