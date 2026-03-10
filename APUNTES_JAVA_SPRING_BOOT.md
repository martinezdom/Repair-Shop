# Apuntes Basicos de Java y Spring Boot

## 1. Fundamentos de Java (POO)

### 1.1 Herencia (`extends`)

La herencia permite crear una nueva clase basada en una clase existente.

```java
// Clase padre/superclase
public class Vehiculo {
    protected String marca;
    protected String modelo;

    public void arrancar() {
        System.out.println("El vehiculo arranca");
    }
}

// Clase hija/subclase
public class Coche extends Vehiculo {
    private int numeroPuertas;

    @Override
    public void arrancar() {
        System.out.println("El coche arranca");
    }

    public void mostrarInfo() {
        System.out.println("Marca: " + marca + ", Puertas: " + numeroPuertas);
    }
}
```

Caracteristicas clave:

- `extends`: palabra clave para heredar.
- `protected`: accesible desde subclases.
- `super()`: llama al constructor padre.
- `super.metodo()`: llama a un metodo de la superclase.

### 1.2 Interfaces (`implements`)

Una interface es un contrato que define metodos que deben ser implementados.

```java
// Definicion de interface
public interface Volador {
    void volar();
    void aterrizar();
}

// Otra interface
public interface Navegable {
    void navegar();
}

// Clase que implementa multiples interfaces
public class Avion implements Volador, Navegable {
    @Override
    public void volar() {
        System.out.println("Avion volando");
    }

    @Override
    public void aterrizar() {
        System.out.println("Avion aterrizando");
    }

    @Override
    public void navegar() {
        System.out.println("Avion navegando por el aire");
    }
}
```

Interface vs Clase Abstracta:

| Interface | Clase abstracta |
| --- | --- |
| Permite multiple `implements` | Solo un `extends` |
| Metodos abstractos por defecto | Puede tener metodos concretos |
| No tiene constructor | Si tiene constructor |
| Solo constantes (`public static final`) | Puede tener atributos de instancia |

### 1.3 Anotacion `@Override`

Indica que un metodo esta sobrescribiendo un metodo de la superclase.

```java
public class Animal {
    public void hacerSonido() {
        System.out.println("Sonido generico");
    }
}

public class Perro extends Animal {
    @Override
    public void hacerSonido() {
        System.out.println("Guau guau");
    }
}
```

Beneficios:

- Validacion en tiempo de compilacion.
- Mejor legibilidad del codigo.
- Previene errores tipograficos.

## 2. Spring Boot - Anotaciones Basicas

### 2.1 Anotaciones de Persistencia (JPA/Hibernate)

#### `@Entity`

Marca una clase como entidad de base de datos.

```java
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    // campos y metodos
}
```

#### `@Table`

Especifica detalles de la tabla en la base de datos.

```java
@Entity
@Table(
    name = "empleados",
    schema = "recursos_humanos",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
    }
)
public class Empleado {
    // ...
}
```

#### `@Id`

Define la clave primaria.

```java
@Entity
public class Producto {
    @Id
    private Long id;
}
```

#### `@GeneratedValue`

Define la estrategia de generacion de valores para la clave primaria.

```java
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
```

Ejemplos de estrategias:

```java
@GeneratedValue(strategy = GenerationType.AUTO)
private Long codigo;

@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
@SequenceGenerator(name = "seq_gen", sequenceName = "producto_seq")
private Long secuencia;
```

#### `@Column`

Configura el mapeo de una columna.

```java
@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(updatable = false)
    private java.time.LocalDateTime fechaCreacion;
}
```

Atributos comunes de `@Column`:

| Atributo | Descripcion | Ejemplo |
| --- | --- | --- |
| `name` | Nombre de la columna | `name = "user_name"` |
| `nullable` | Si permite `NULL` | `nullable = false` |
| `unique` | Valor unico | `unique = true` |
| `length` | Longitud maxima (String) | `length = 50` |
| `precision`/`scale` | Precision numerica | `precision = 10, scale = 2` |
| `insertable`/`updatable` | Control de operaciones SQL | `updatable = false` |

#### `@Repository`

Indica que la clase/interfaz es un repositorio (DAO).

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.activo = true")
    List<Usuario> findUsuariosActivos();
}
```

Caracteristicas:

- Traduce excepciones de persistencia a excepciones de Spring.
- Marca un bean de acceso a datos.
- Spring lo detecta y lo instancia automaticamente.

### 2.2 Otras Anotaciones Importantes de Spring

#### `@Service`

Para la logica de negocio (capa de servicios).

```java
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
```

#### `@RestController`

Controlador para APIs REST.

```java
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.obtenerTodos();
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }
}
```

#### `@Autowired`

Inyeccion automatica de dependencias.

```java
@Component
public class MiClase {
    @Autowired
    private UsuarioService usuarioService; // Inyeccion por campo

    private EmailService emailService;

    @Autowired
    public MiClase(EmailService emailService) { // Inyeccion por constructor (recomendada)
        this.emailService = emailService;
    }

    @Autowired
    public void setOtroServicio(OtroServicio otroServicio) { // Inyeccion por setter
        // ...
    }
}
```

## 3. Ejemplo Completo

### 3.1 Modelo / Entidad

```java
package com.ejemplo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_limite")
    private LocalDateTime fechaLimite;

    @Column(nullable = false)
    private Boolean completada = false;

    public Tarea() {
    }

    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // ... otros getters y setters
}
```

### 3.2 Repositorio

```java
package com.ejemplo.repository;

import com.ejemplo.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    // Metodo derivado del nombre
    List<Tarea> findByCompletada(Boolean completada);

    // Query personalizada con JPQL
    @Query("SELECT t FROM Tarea t WHERE t.titulo LIKE %:keyword%")
    List<Tarea> buscarPorTitulo(@Param("keyword") String keyword);

    // Query nativa SQL
    @Query(value = "SELECT * FROM tareas WHERE fecha_limite < NOW()", nativeQuery = true)
    List<Tarea> findTareasVencidas();
}
```

## 4. Resumen Rapido

| Anotacion | Proposito | Ubicacion tipica |
| --- | --- | --- |
| `@Entity` | Clase mapeada a BD | Clase modelo |
| `@Table` | Configuracion de tabla | Clase modelo |
| `@Id` | Clave primaria | Campo |
| `@GeneratedValue` | Estrategia de ID | Campo ID |
| `@Column` | Configuracion de columna | Campos |
| `@Repository` | Bean de acceso a datos | Clase/interfaz DAO |
| `@Service` | Bean de logica de negocio | Clase de servicio |
| `@RestController` | Bean de API REST | Controlador |
| `@Autowired` | Inyeccion de dependencia | Campos/constructores |
| `@Override` | Sobrescritura de metodo | Metodos |

Flujo tipico en Spring Boot:

1. Modelo (`@Entity`): representa tabla de BD.
2. Repositorio (`@Repository`): operaciones CRUD.
3. Servicio (`@Service`): logica de negocio.
4. Controlador (`@RestController`): endpoints API.
5. Cliente: consume la API.

## 5. Arrays y Colecciones en Java

### 5.1 Diferencia entre array y coleccion

En Java, `hash` no es un tipo de array. Lo correcto es distinguir entre:

- `Array`: estructura de tamano fijo.
- `List`: coleccion ordenada que permite duplicados.
- `Set`: coleccion sin duplicados.
- `Map`: estructura clave-valor.

### 5.2 Comparativa rapida

| Estructura | Tamano | Orden | Duplicados | Acceso principal | Uso tipico |
| --- | --- | --- | --- | --- | --- |
| `int[]`, `String[]` | Fijo | Si, por indice | Si | Muy rapido por indice | Cuando sabes el tamano desde el inicio |
| `ArrayList<E>` | Dinamico | Si | Si | Rapido por indice | Listas generales |
| `LinkedList<E>` | Dinamico | Si | Si | Mejor para insertar/eliminar en extremos | Colas o listas con muchas inserciones |
| `HashSet<E>` | Dinamico | No garantizado | No | Busqueda rapida | Evitar elementos repetidos |
| `LinkedHashSet<E>` | Dinamico | Orden de insercion | No | Busqueda rapida | Set sin duplicados manteniendo orden |
| `TreeSet<E>` | Dinamico | Ordenado | No | Acceso ordenado | Datos sin duplicados y ordenados |
| `HashMap<K, V>` | Dinamico | No garantizado | Claves no repetidas | Busqueda por clave | Diccionarios y busquedas por clave |
| `LinkedHashMap<K, V>` | Dinamico | Orden de insercion | Claves no repetidas | Busqueda por clave | Mapas con orden predecible |
| `TreeMap<K, V>` | Dinamico | Ordenado por clave | Claves no repetidas | Busqueda por clave ordenada | Mapas ordenados |

### 5.3 Tipos de arrays en Java

| Tipo | Ejemplo | Descripcion |
| --- | --- | --- |
| Array de primitivos | `int[] numeros` | Guarda valores primitivos |
| Array de objetos | `String[] nombres` | Guarda referencias a objetos |
| Array multidimensional | `int[][] matriz` | Es un array de arrays |

Ejemplos:

```java
int[] edades = {18, 20, 25};
String[] usuarios = {"Ana", "Luis", "Marta"};
int[][] tablero = {
    {1, 2, 3},
    {4, 5, 6}
};
```

### 5.4 Ejemplos de colecciones

```java
List<String> lista = new ArrayList<>();
lista.add("Ana");
lista.add("Ana");

Set<String> conjunto = new HashSet<>();
conjunto.add("Ana");
conjunto.add("Ana"); // no se repite

Map<Long, String> usuariosPorId = new HashMap<>();
usuariosPorId.put(1L, "Ana");
usuariosPorId.put(2L, "Luis");
```

### 5.5 Regla practica

1. Usa `array` si el tamano no va a cambiar.
2. Usa `ArrayList` si necesitas una lista dinamica.
3. Usa `HashSet` si quieres evitar duplicados.
4. Usa `HashMap` si necesitas relacionar una clave con un valor.
5. Usa `TreeSet` o `TreeMap` si necesitas datos ordenados.

## 6. Anexo SQL (Tabla `users`)

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
