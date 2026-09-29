# Parcial 1 — Estaciones y rutas (Spring Boot)

Solución del primer parcial práctico del curso de Desarrollo de Software (Universidad de los Andes, sección 6, 2025-10). Parte de la plantilla del curso y agrega la lógica de negocio que relaciona **estaciones** y **rutas** de transporte, en una relación muchos a muchos.

## Qué implementa

Entidades JPA (`EstacionEntity`, `RutaEntity`) y el servicio `RutaEstacionService`, con dos operaciones y sus reglas de negocio:

| Operación | Regla |
| --- | --- |
| `addEstacionRuta` | Una estación con capacidad menor a 100 pasajeros no puede tener más de 2 rutas circulares. |
| `removeEstacionRuta` | No se puede quitar de una estación su última ruta nocturna. |

Ambas lanzan `EntityNotFoundException` si la estación o la ruta no existen, y `IllegalStateException` si se viola la regla.

## Tecnologías

Java 21, Spring Boot 3.2, Spring Data JPA, H2 en memoria, Lombok, Podam (datos de prueba), JUnit 5 y Maven.

## Cómo ejecutarlo

Requisitos: JDK 21.

```bash
git clone https://github.com/Alejob12/ISIS2603_202510_S6_Parcial1.git
cd ISIS2603_202510_S6_Parcial1
./mvnw test           # pruebas del servicio
./mvnw spring-boot:run
```

Con la aplicación arriba, la consola de H2 queda en `http://localhost:8080/api/h2-console` (URL `jdbc:h2:mem:parcial1`).

## Pruebas

`RutaEstacionServiceTest` usa `@DataJpaTest` y cubre las dos operaciones: el caso exitoso, la estación o ruta inexistente y cada una de las reglas de negocio.

## Estructura

```
src/main/java/co/edu/uniandes/dse/parcial1/
  entities/      BaseEntity, EstacionEntity, RutaEntity
  repositories/  EstacionRepository, RutaRepository
  services/      RutaEstacionService
  exceptions/    EntityNotFoundException, manejador de errores REST
src/test/java/.../services/RutaEstacionServiceTest.java
```

## Autor

**Alejandro Bernal López** — Ingeniería de Sistemas e Industrial, Universidad de los Andes.
