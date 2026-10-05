# Gestor de Tareas — Spring Boot + Angular

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?logo=springboot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-20-DD0031?logo=angular&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white)

Aplicación **CRUD full-stack** de tareas, construida con **Clean Architecture**
en el backend y en el frontend.

> **Alcance del proyecto.** Es un proyecto de práctica enfocado en demostrar un
> CRUD full-stack (Java + Angular) y la aplicación de **Clean Architecture**.
> **No incluye autenticación ni autorización a propósito**: queda fuera del
> alcance. No está pensado para desplegarse en producción tal cual.

## Capturas

![Vista principal: formulario de alta y listado de tareas](docs/screenshots/app.png)

## Stack

| Capa | Tecnología |
|------|-----------|
| Backend | Java 17, Spring Boot 4, Spring Data JPA, Bean Validation |
| Base de datos | PostgreSQL |
| Frontend | Angular 20 (standalone components + signals), SCSS |
| Build | Maven (wrapper), Angular CLI |

## Arquitectura

Ambos proyectos siguen **Clean Architecture** (Ports & Adapters): las
dependencias apuntan hacia adentro y el dominio no conoce frameworks.

```
dominio        → entidad Tarea y reglas de negocio puras
  ↑
aplicación     → casos de uso + puertos (in/out)
  ↑
infraestructura→ adaptadores REST/HTTP y persistencia JPA
```

- **Dominio**: `Tarea`, `EstadoTarea` y sus invariantes (sin Spring ni JPA).
- **Aplicación**: casos de uso (`TareaUseCase`) y puertos de entrada/salida.
- **Infraestructura**: controller REST, DTOs, persistencia (Spring Data JPA) y
  configuración. La entidad JPA vive aquí, separada del modelo de dominio.

## Estructura del repositorio

```
.
├── backend/            API REST (Spring Boot)
│   └── src/main/java/crud/java_angular/demo/
│       ├── domain/          modelo + excepciones de dominio
│       ├── application/     casos de uso + puertos
│       └── infrastructure/  web (controller/dto), persistencia, config
├── frontend/           SPA (Angular)
│   └── src/app/
│       ├── core/            domain (modelos, puertos) + application (use cases)
│       ├── infrastructure/  adaptadores HTTP (repos, dto, mappers)
│       └── presentation/    componentes (página, formulario, lista)
└── docs/screenshots/   imágenes para el README
```

## Requisitos

- **Java 17+** (compilado con JDK 25).
- **Node 20+** y **npm**.
- **PostgreSQL** con una base `tareas_db` creada.

## Puesta en marcha

### 1. Base de datos

Creá la base (una vez):

```sql
CREATE DATABASE tareas_db;
```

El esquema lo crea Hibernate automáticamente al arrancar (`ddl-auto=update`).

### 2. Backend

La configuración sensible se define en un archivo **`.env`** (ignorado por git;
nunca se commitea). Copiá la plantilla y completá tus valores:

```bash
cd backend
copy .env.example .env      # Linux/macOS: cp .env.example .env
```

Editá `.env` con tu contraseña (y el puerto si tu PostgreSQL no usa el 5432):

```dotenv
DB_URL=jdbc:postgresql://localhost:5432/tareas_db
DB_USERNAME=postgres
DB_PASSWORD=tu_password_de_postgres
```

Y arrancá:

```powershell
.\mvnw.cmd spring-boot:run
```

Spring Boot carga el `.env` automáticamente (vía `spring.config.import`).
Alternativamente podés exportar las mismas claves como variables de entorno reales.

| Variable | Default | Descripción |
|----------|---------|-------------|
| `DB_PASSWORD` | — (obligatoria) | Contraseña del usuario de PostgreSQL |
| `DB_URL` | `jdbc:postgresql://localhost:5432/tareas_db` | URL JDBC |
| `DB_USERNAME` | `postgres` | Usuario de PostgreSQL |

El backend arranca en `http://localhost:8080` y carga unas tareas de ejemplo
la primera vez (si la tabla está vacía). CORS habilitado para `http://localhost:4200`.

### 3. Frontend

```bash
cd frontend
npm install        # la primera vez
npx ng serve
```

Disponible en `http://localhost:4200`, consumiendo la API en `http://localhost:8080/api`.

## API

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/tareas` | Listar tareas (filtro opcional `?estado=PENDIENTE`) |
| GET | `/api/tareas/{id}` | Obtener una tarea |
| POST | `/api/tareas` | Crear una tarea |
| PUT | `/api/tareas/{id}` | Editar una tarea |
| DELETE | `/api/tareas/{id}` | Eliminar una tarea |

Una tarea tiene: `titulo`, `descripcion`, `estado`
(`PENDIENTE` · `EN_PROGRESO` · `COMPLETADA`) y `fechaVencimiento`.

## Tests

```bash
cd backend
.\mvnw.cmd test
```

Cubren el dominio, el caso de uso (con Mockito) y el controller (slice web).
