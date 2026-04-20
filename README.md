# Repair-Shop API (SaaS MVP)

![Java](https://img.shields.io/badge/Java-17%2F21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-REST_API-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)

## Descripción

Repair-Shop es una API REST para un sistema de gestión de talleres de reparación. El objetivo del proyecto es centralizar operaciones habituales del negocio, como la administración de usuarios y roles, el seguimiento del estado de las reparaciones y el control de inventario de piezas, dentro de una base backend mantenible y preparada para evolucionar a un MVP SaaS.

El repositorio está en una etapa inicial de construcción. La base actual ya incluye configuración de persistencia con Spring Data JPA, integración con MySQL y el modelo inicial de usuarios. El alcance funcional del MVP incluye autenticación/autorización, gestión de reparaciones e inventario, que se irán incorporando de forma incremental.

## Características

- API REST construida con Spring Boot.
- Persistencia con Spring Data JPA e Hibernate.
- Integración con MySQL mediante variables de entorno.
- Modelo inicial de usuarios con soporte de roles.
- Validación de esquema con Hibernate en el arranque (`ddl-auto=validate`).
- Arquitectura preparada para capas Controller, Service y Repository.
- Autenticación y autorización implementadas con Spring Security + JWT.
- CRUD completo de reparaciones (crear, listar, consultar, actualizar y eliminar).
- Filtrado de reparaciones por estado mediante Enum (`RepairStatus`).
- Integración de seguridad en endpoints con identidad autenticada por token (incluye `@AuthenticationPrincipal` para recursos del mecánico autenticado).
- Dashboard de estadísticas de negocio implementado con consultas JPQL (coches pendientes e ingresos por reparaciones terminadas).

## Arquitectura y Patrones

La API sigue una arquitectura multicapa orientada a separar responsabilidades:

- `Controller`: expone endpoints HTTP y gestiona la capa web.
- `Service`: concentra reglas de negocio y orquestación de casos de uso.
- `Repository`: encapsula el acceso a datos con Spring Data JPA.
- `Entity`: representa el modelo persistente de la base de datos.

Estructura base del código:

```text
src/main/java/io/github/martinezdom/repairshop/
|- controllers/
|- services/
|- repositories/
|- entities/
`- RepairshopApplication.java
```

El proyecto está planteado para trabajar con DTOs (Data Transfer Objects) en la frontera de la API, de forma que las entidades JPA no se expongan directamente en requests/responses. Esto permite desacoplar el modelo persistente del contrato HTTP, mejorar la validación de entrada y facilitar cambios futuros sin romper clientes.

## Stack Tecnológico

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven

Nota: como contexto de portfolio, el stack objetivo del MVP está planteado alrededor de Java 21 y Spring Boot 4.0.3.

## Requisitos Previos

Antes de ejecutar el proyecto en local necesitas tener instalado:

- JDK 21
- Maven 3.9+ o usar el Maven Wrapper incluido en el repositorio
- MySQL 8 o compatible

## Configuración Local y Variables de Entorno

La aplicación obtiene la configuración de la base de datos desde variables de entorno:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

Configuración actual en `src/main/resources/application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
```

### Opción 1: usar variables de entorno

Ejemplo de valores:

```text
DB_URL=jdbc:mysql://localhost:3306/repair_shop_db?useSSL=false&serverTimezone=UTC
DB_USER=root
DB_PASSWORD=tu_password
```

### Opción 2: crear un perfil local

Si prefieres no depender de variables de entorno durante desarrollo, puedes crear un archivo local no versionado como `src/main/resources/application-local.properties` con valores concretos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/repair_shop_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

Después puedes ejecutar la aplicación con el perfil `local`.

Importante: no subas credenciales reales al repositorio.

## Ejecución

1. Crea la base de datos en MySQL.
2. Configura las variables de entorno o el perfil local.
3. Ejecuta la aplicación.

Con Maven:

```bash
mvn spring-boot:run
```

Con Maven Wrapper en Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Con perfil local:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Documentación de la API

Una vez arrancada la aplicación, puedes acceder a la documentación interactiva y probar los endpoints desde:

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

_Nota: Para los endpoints protegidos, primero obtén un token en `/api/auth/login` y utiliza el botón "Authorize" de Swagger._

Credenciales demo (si cargas `schemas/data_dump.sql`):

- Email: `demo.mecanico@repairshop.local`
- Password: `example1234`

## Estructura de la Base de Datos

El proyecto utiliza Hibernate con `ddl-auto=validate`. El esquema debe estar creado previamente.

En la raíz del proyecto encontrarás la carpeta `/schemas` con los scripts necesarios:

- `schema_users.sql`, `schema_customers.sql`, etc.: Definición individual de tablas.
- `data_dump.sql` (opcional): Incluye datos de prueba (mecánicos, vehículos y reparaciones) para probar el Dashboard nada más arrancar.

Para inicializar la base de datos:

1. Crea la base de datos: `CREATE DATABASE repair_shop_db;`
2. Ejecuta los scripts de la carpeta `/schemas` en tu cliente SQL favorito.

## Estado del Proyecto

El MVP Core está terminado e integra Seguridad (JWT), módulo de Reparaciones, Dashboard de Estadísticas y Documentación Swagger/OpenAPI en una misma base funcional.

Las siguientes iteraciones planificadas son:

- Paginación en endpoints de listado.
- Dockerización para despliegue reproducible.
- Pipeline de CI/CD para build, tests y calidad automatizada.
