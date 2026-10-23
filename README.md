# Proyecto Clínica Dental (Versión Servlets + DAO + MVC)

Gestión de citas de pacientes de una clínica dental, refactorizado desde una arquitectura inicial basada en scriptlets a una arquitectura limpia **Modelo-Vista-Controlador (MVC)** utilizando **Servlets, JavaBeans, DAOs, JSTL (Expression Language) y Jakarta EE 10 sobre Tomcat 11**.

## Estructura del Proyecto

```text
dwes_servlet_dentista/
├── pom.xml
└── src/main/
    ├── java/
    │   └── es/dwes/dentista/
    │       ├── controlador/
    │       │   ├── PacienteServlet.java    <- Controller para gestión de pacientes
    │       │   └── CitaServlet.java        <- Controller para gestión de citas
    │       └── modelo/
    │           ├── ConexionBD.java         <- Gestor centralizado de conexiones JDBC
    │           ├── Paciente.java           <- JavaBean de la entidad Paciente
    │           ├── Odontologo.java         <- JavaBean de la entidad Odontólogo
    │           ├── Cita.java               <- JavaBean de la entidad Cita
    │           ├── PacienteDAO.java        <- DAO con lógica SQL para pacientes
    │           ├── OdontologoDAO.java      <- DAO con lógica SQL para odontólogos
    │           └── CitaDAO.java            <- DAO con lógica SQL para citas
    ├── resources/
    │   └── dentista.sql                    <- Script de creación de la base de datos
    └── webapp/
        ├── index.jsp                       <- Menú principal
        ├── listadoPacientes.jsp            <- Vista de listado de pacientes (JSTL / EL)
        ├── listadoCitas.jsp                <- Vista de citas próximas / histórico (JSTL / EL)
        ├── formularioPaciente.jsp          <- Formulario de alta de paciente
        ├── formularioCita.jsp              <- Formulario de alta de cita
        └── estilos.css                     <- Hoja de estilos global
```

> **Refactorización:** Se han eliminado los antiguos archivos JSP auxiliares de acción (`grabaPaciente.jsp`, `borraPaciente.jsp`, `grabaCita.jsp` y `borraCita.jsp`). Toda la lógica de procesamiento, navegación, conexión JDBC y consultas SQL ha sido trasladada a los **Servlets controladores** y clases **DAO**, dejando las páginas JSP como vistas limpias sin scriptlets Java.

## Base de datos

Base de datos `dentista` con tres tablas:

| Tabla         | Campo clave     | Contenido                                                                      |
|---------------|-----------------|--------------------------------------------------------------------------------|
| `paciente`    | `id_paciente`   | `nombre`, `apellidos`, `dni`, `fecha_nacimiento`, `telefono`, `email`, `direccion`, `alergias` |
| `odontologos` | `id_odontologo` | `nombre`, `especialidad`                                                       |
| `cita`        | `id_cita`       | `fecha`, `hora`, `id_paciente` (FK), `id_odontologo` (FK), `motivo`            |

Relaciones:
- `cita.id_paciente` → `paciente.id_paciente`, con `ON DELETE CASCADE`: al borrar un paciente se eliminan automáticamente sus citas asociadas.
- `cita.id_odontologo` → `odontologos.id_odontologo`: referencia al profesional que atiende la cita.

## Prerrequisitos e Instalación

1. **Importar la base de datos** `src/main/resources/dentista.sql`:
   ```bash
   mysql -u user -puser < src/main/resources/dentista.sql
   ```
   *Servidor MySQL escuchando en `localhost:3306` con usuario `user` y contraseña `user`.*

2. **Entorno de ejecución**: Java 21, Apache Maven 3.8+ y servidor **Apache Tomcat 11.0.x** (compatible con Jakarta EE 10).

## Plataforma: Tomcat 11 (Jakarta EE 10)

La aplicación utiliza el espacio de nombres **`jakarta.*`** acorde con las especificaciones modernas de Jakarta EE 10:

| Especificación | Versión | Artefacto Maven                                      |
|----------------|---------|-----------------------------------------------------|
| Servlet API    | 6.0     | `jakarta.platform:jakarta.jakartaee-web-api:10.0.0` |
| JSTL           | 2.0     | `org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0`  |

## Compilación y Despliegue

Para construir la aplicación y generar el archivo `.war`:

```bash
mvn clean package
```

Esto generará el paquete `target/servlet_dentista.war` desplegable en Tomcat 11. El contexto base de la aplicación es `/servlet_dentista`.

## Notas de Arquitectura e Implementación

- **Patrón MVC**: 
  - **Modelo**: Clases JavaBeans (`Paciente`, `Odontologo`, `Cita`) junto con las clases DAO (`PacienteDAO`, `OdontologoDAO`, `CitaDAO`) y la clase auxiliar `ConexionBD` para centralizar los parámetros JDBC.
  - **Vista**: Páginas JSP en la carpeta `webapp/` que renderizan el contenido mediante **JSTL / Expression Language (EL)** sin scriptlets Java.
  - **Controlador**: Servlets `PacienteServlet` (mapeado a `/pacientes`) y `CitaServlet` (mapeado a `/citas`) que reciben las peticiones HTTP (`GET`/`POST`), invocan a los DAOs y redirigen o envían el flujo a la vista correspondiente.
- **Rutas y Navegación**:
  - `pacientes?accion=listar` → Muestra el listado de pacientes.
  - `pacientes?accion=formulario` → Abre el formulario para registrar un nuevo paciente.
  - `citas?accion=listar` → Muestra las próximas citas (añadir `&todas=1` para consultar el histórico completo).
  - `citas?accion=formulario` → Abre el formulario para registrar una cita con selectores dinámicos cargados desde los DAOs.