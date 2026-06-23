# 🚀 Proyecto de Gestión de Personas - Historias de Usuario

Este proyecto consiste en una API REST robusta construida con **Spring Boot 3**, diseñada bajo los lineamientos de la **Arquitectura Hexagonal (Clean Architecture)** y orientada al cumplimiento de historias de usuario con estrictos criterios de aceptación técnicos y de seguridad (OWASP Top 10).

---

## 🛠️ Tecnologías y Entorno de Desarrollo (HU-01)

* **Java versión:** 17+ configurado mediante la variable de entorno `JAVA_HOME`.
* **IDE Recomendado:** IntelliJ IDEA.
* **Gestor de Dependencias:** **Gradle** (Seleccionado en reemplazo de Maven por optimización de tiempos de compilación, flexibilidad en tareas automatizadas y legibilidad superior en el script de construcción `build.gradle`).
* **Base de Datos:** PostgreSQL para entornos locales/producción y H2 Database en memoria para la ejecución automatizada y aislada de la suite de pruebas.

---

## 📐 Estructura Base y Equivalencia Arquitectónica (HU-03)

El proyecto implementa la **Arquitectura Hexagonal** para desacoplar las reglas de negocio de los componentes tecnológicos y frameworks. A continuación se detalla la equivalencia con la arquitectura clásica de tres capas:

* **Controller** ➔ Mapeado en la capa de `infraestructura.controlador` (Acoplado a Spring Web).
* **Service** ➔ Dividido de forma cohesiva en `aplicacion.manejador` (Orquestadores de Casos de Uso) y `dominio.servicio` (Reglas puras de negocio).
* **Repository** ➔ Disociado en `dominio.puerto` (Contratos/Interfaces de persistencia) e `infraestructura.adaptador.repositorio` (Implementación real con Spring Data JPA).
* **Domain** ➔ Ubicado en la capa pura `dominio.modelo`, libre de dependencias de frameworks externos.

### 🗺️ Diagrama de Arquitectura del Sistema

```mermaid
graph LR

subgraph Infrastructure [Capa de Infraestructura]
A[PersonaControlador]
B[PersonaAdaptadorServicio]
C[JpaPersonaRepositorio]
M[PersonaSpecification]
end

subgraph Application [Capa de Aplicación]
D[ManejadorCrearPersona]
E[ManejadorActualizarPersona]
F[ManejadorBuscarPersonaPorId]
G[ManejadorEliminarPersona]
H[ManejadorListarPersonas]
N[ManejadorBuscarPersonasAvanzado]
end

subgraph Domain [Capa de Dominio]
J[PersonaRepositorio]
K[Persona]
L[ExcepcionNegocio]
O[PersonaSearchCriteria]
P[PaginaResultado]
end

A --> D
A --> E
A --> F
A --> G
A --> H
A --> N

D --> J
E --> J
F --> J
G --> J
H --> J
N --> J

B -. implementa .-> J
B --> C
B --> M

J --> K
J --> L
N --> O
N --> P