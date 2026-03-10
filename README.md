# Repair-Shop API (SaaS MVP)

![Java](https://img.shields.io/badge/Java-17%2F21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-REST_API-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)

## Descripcion

Repair-Shop es una API REST para un sistema de gestion de talleres de reparacion. El objetivo del proyecto es centralizar operaciones habituales del negocio, como la administracion de usuarios y roles, el seguimiento del estado de las reparaciones y el control de inventario de piezas, dentro de una base backend mantenible y preparada para evolucionar a un MVP SaaS.

El repositorio esta en una etapa inicial de construccion. La base actual ya incluye configuracion de persistencia con Spring Data JPA, integracion con MySQL y el modelo inicial de usuarios. El alcance funcional del MVP incluye autenticacion/autorizacion, gestion de reparaciones e inventario, que se iran incorporando de forma incremental.

## Features

- API REST construida con Spring Boot.
- Persistencia con Spring Data JPA e Hibernate.
- Integracion con MySQL mediante variables de entorno.
- Modelo inicial de usuarios con soporte de roles.
- Validacion de esquema con Hibernate en el arranque (`ddl-auto=validate`).
- Arquitectura preparada para capas Controller, Service y Repository.
- Autenticacion y autorizacion con Spring Security + JWT como siguiente iteracion.
- Gestion de reparaciones y estados como alcance funcional del MVP.
- Gestion de inventario de piezas como alcance funcional del MVP.

## Arquitectura y Patrones

La API sigue una arquitectura multicapa orientada a separar responsabilidades:

- `Controller`: expone endpoints HTTP y gestiona la capa web.
- `Service`: concentra reglas de negocio y orquestacion de casos de uso.
- `Repository`: encapsula el acceso a datos con Spring Data JPA.
- `Entity`: representa el modelo persistente de la base de datos.

Estructura base del codigo:

```text
src/main/java/io/github/martinezdom/repairshop/
|- controllers/
|- services/
|- repositories/
|- entities/
`- RepairshopApplication.java
```

El proyecto esta planteado para trabajar con DTOs (Data Transfer Objects) en la frontera de la API, de forma que las entidades JPA no se expongan directamente en requests/responses. Esto permite desacoplar el modelo persistente del contrato HTTP, mejorar la validacion de entrada y facilitar cambios futuros sin romper clientes.

## Stack Tecnologico

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL
- Maven

Nota: como contexto de portfolio, el stack objetivo del MVP esta planteado alrededor de Java 21 y Spring Boot 4.0.3.

## Requisitos Previos

Antes de ejecutar el proyecto en local necesitas tener instalado:

- JDK 21
- Maven 3.9+ o usar el Maven Wrapper incluido en el repositorio
- MySQL 8 o compatible

## Configuracion Local y Variables de Entorno

La aplicacion obtiene la configuracion de la base de datos desde variables de entorno:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

Configuracion actual en `src/main/resources/application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
```

### Opcion 1: usar variables de entorno

Ejemplo de valores:

```text
DB_URL=jdbc:mysql://localhost:3306/repair_shop_db?useSSL=false&serverTimezone=UTC
DB_USER=root
DB_PASSWORD=tu_password
```

### Opcion 2: crear un perfil local

Si prefieres no depender de variables de entorno durante desarrollo, puedes crear un archivo local no versionado como `src/main/resources/application-local.properties` con valores concretos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/repair_shop_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

Despues puedes ejecutar la aplicacion con el perfil `local`.

Importante: no subas credenciales reales al repositorio.

## Ejecucion

1. Crea la base de datos en MySQL.
2. Configura las variables de entorno o el perfil local.
3. Ejecuta la aplicacion.

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

## Estructura de la Base de Datos

El proyecto utiliza Hibernate con la estrategia:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Eso significa que Hibernate valida el esquema existente, pero no crea automaticamente la base de datos ni las tablas. Antes de arrancar la aplicacion debes crear manualmente la base `repair_shop_db` y asegurarte de que el esquema ya existe.

Ejemplo minimo:

```sql
CREATE DATABASE repair_shop_db;
```

Si las tablas aun no existen, deberas crearlas manualmente mediante scripts SQL o futuras migraciones. Como referencia, este puede ser un `schema.sql` minimo para que la tabla `users` sea compatible con el modelo actual:

```sql
CREATE TABLE users (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(255) NOT NULL UNIQUE,
	email VARCHAR(255) NOT NULL UNIQUE,
	password_hash VARCHAR(255) NOT NULL,
	role VARCHAR(50) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Estado del Proyecto

Actualmente incluye la configuracion principal del servicio, el modelo de usuario y la capa de persistencia inicial. Las siguientes iteraciones incorporaran autenticacion JWT, endpoints REST completos, gestion de reparaciones, inventario y autorizacion por roles.