package com.practica.historias.infraestructura.adaptador.repositorio;

import com.practica.historias.dominio.modelo.PersonaSearchCriteria;
import com.practica.historias.infraestructura.adaptador.entidad.PersonaEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonaSpecification {

    public static Specification<PersonaEntity> conCriterios(PersonaSearchCriteria criterios) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criterios.getNombre() != null && !criterios.getNombre().isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nombre")),
                        "%" + criterios.getNombre().toLowerCase() + "%"
                ));
            }

            if (criterios.getApellido() != null && !criterios.getApellido().isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("apellido")),
                        "%" + criterios.getApellido().toLowerCase() + "%"
                ));
            }

            LocalDate hoy = LocalDate.now();
            if (criterios.getEdadMin() != null) {
                LocalDate fechaMax = hoy.minusYears(criterios.getEdadMin());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaNacimiento"), fechaMax));
            }

            if (criterios.getEdadMax() != null) {
                LocalDate fechaMin = hoy.minusYears(criterios.getEdadMax() + 1).plusDays(1);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaNacimiento"), fechaMin));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}