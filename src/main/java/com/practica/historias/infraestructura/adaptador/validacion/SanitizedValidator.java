package com.practica.historias.infraestructura.adaptador.validacion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SanitizedValidator implements ConstraintValidator<Sanitized, String> {

    private static final Pattern XSS_PATTERN = Pattern.compile(
            "<script(?:>|\\s|\\/)|javascript:|expression\\(|<\\s*\\/\\s*script\\s*>",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        return !XSS_PATTERN.matcher(value).find();
    }
}