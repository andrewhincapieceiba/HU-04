package com.practica.historias.dominio.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SanitizedValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Sanitized {
    String message() default "El campo contiene caracteres o scripts no permitidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}