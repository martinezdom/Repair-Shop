# Repair-Shop-Backend API

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-REST_API-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?logo=springsecurity&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-Migrations-CC0200?logo=flyway&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?logo=swagger&logoColor=black)

API REST para gestionar un taller de reparaciones. Cubre autenticación con JWT, usuarios, clientes, vehículos, reparaciones y estadísticas básicas del negocio.

## Qué incluye

- Spring Boot + Spring Web MVC.
- Spring Data JPA + Hibernate.
- MySQL como base de datos.
- Flyway para el control de versiones y migraciones de la base de datos.
- Autenticación y autorización con Spring Security y JWT.
- Documentación automática con Swagger.
- CRUD de reparaciones y filtro por estado.
- Dashboard con consultas JPQL.
- Paginación en las listas principales.
- DTOs en la capa HTTP para no exponer las entidades directamente.

## Requisitos

- JDK 21.
- Maven 3.9+ o Maven Wrapper.
- MySQL 8 o Docker.
- Docker si quieres levantarlo todo en contenedores.

## Configuración local

La aplicación lee la base de datos desde variables de entorno:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`
- `JWT_SECRET`

Ejemplo:

```text
DB_URL=jdbc:mysql://localhost:3306/repairshop?useSSL=false&serverTimezone=UTC
DB_USER=root
DB_PASSWORD=example1234
JWT_SECRET=repairshop-demo-secret
```

Si prefieres trabajar sin variables de entorno, puedes crear un perfil local con tus propios valores y arrancar Spring con ese perfil.

## Arranque rápido con Docker

El proyecto trae un `docker-compose.yml` con MySQL y la API.

```bash
docker compose up --build
```

Credenciales demo usadas en Docker:

- MySQL root password: `example1234`
- Login demo del taller: `demo.mecanico@repairshop.local` / `example1234`

## Ejecución sin Docker

1. Crea la base de datos `repairshop`.
2. Define las variables de entorno.
3. Arranca la app con Maven:

```bash
mvn spring-boot:run
```

Si usas el perfil local:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Swagger

Una vez levantada la aplicación:

- Swagger UI: http://localhost:8080/swagger-ui.html

Para probar endpoints protegidos, primero obtén token en `/api/auth/login` y luego usa el botón Authorize.

En `schemas/` tienes un `05_data_dump.sql` opcional con datos de ejemplo. Ese dump viene bien para probar el dashboard y el login demo. Como ahora Flyway gestiona las tablas, puedes importar este archivo manualmente en tu cliente SQL **después** de arrancar la aplicación por primera vez.

## Notas

- Flyway está configurado para crear automáticamente el esquema de la base de datos al arrancar (`spring.flyway.enabled=true`).
- `spring.jpa.hibernate.ddl-auto=validate`, así que Flyway se encarga de todo y Hibernate solo valida.
- La idea del proyecto es ir creciendo por módulos sin mezclar la lógica de negocio con la capa web.
