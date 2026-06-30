# LoginYRegistro

Aplicación Spring Boot que implementa un sistema de **Login y Registro** con formularios JSP, validaciones de campos, y encriptación de contraseñas con BCrypt.

## Objetivos del proyecto

- Crear formularios de registro y login con validaciones y seguridad.
- Practicar la construcción de modelos de dominio, repositorios, servicios, controladores y vistas JSP en una aplicación Spring Boot.

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.5.16**
- **Spring Web (MVC)**
- **Spring Data JPA** + **Hibernate**
- **MySQL** como base de datos
- **JSP + JSTL** para las vistas
- **BCrypt** (`spring-security-crypto`) para encriptar contraseñas
- **Bean Validation** (anotaciones `@NotBlank`, `@Size`, `@Pattern`, `@Email`, `@Past`)

## Estructura del proyecto

```
src/main/java/com/NatyAvello/
 ├── controlador/
 │    └── UsuarioControlador.java
 ├── dto/
 │    ├── RegistroDTO.java
 │    └── LoginDTO.java
 ├── modelo/
 │    └── Usuario.java
 ├── repositorio/
 │    └── UsuarioRepositorio.java
 ├── servicio/
 │    └── UsuarioServicio.java
 └── LoginYRegistroApplication.java

src/main/resources/
 └── application.properties

src/main/webapp/WEB-INF/jsp/
 ├── index.jsp
 └── inicio.jsp
```

## Configuración previa

### 1. Base de datos

Crear la base de datos en MySQL antes de ejecutar la aplicación:

```sql
CREATE DATABASE login_registro_db;
```

La tabla `usuario` se crea automáticamente al iniciar la aplicación, gracias a `spring.jpa.hibernate.ddl-auto=update`.

### 2. Archivo `application.properties`

Ajustar las credenciales de conexión a MySQL según el entorno local:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/login_registro_db
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

## Modelo de dominio: `Usuario`

| Campo | Descripción | Validación |
|---|---|---|
| id | Identificador autogenerado | — |
| nombreUsuario | Nombre de usuario | 3–15 caracteres, único en base de datos |
| contrasena | Contraseña encriptada con BCrypt | Mínimo 8 caracteres, mayúscula, minúscula y número |
| correo | Correo electrónico | Formato de correo válido |
| nombre | Nombre real | Mínimo 3 caracteres, sin números |
| apellido | Apellido | Mínimo 3 caracteres, sin números |
| fechaDeNacimiento | Fecha de nacimiento | Debe ser una fecha en el pasado |
| fechaCreacion | Fecha de creación del registro | Se asigna automáticamente al guardar (`@PrePersist`) |
| fechaActualizacion | Fecha de la última actualización | Se actualiza automáticamente (`@PreUpdate`) |

El campo `confirmacionContrasena` **no existe en el modelo `Usuario`** ni en la base de datos. Vive únicamente en el objeto `RegistroDTO`, usado solo para validar en el formulario que ambas contraseñas coincidan antes de guardar al usuario.

## Flujo de la aplicación

1. **`GET /`** → Muestra `index.jsp` con ambos formularios: Registro y Login.
2. **`POST /procesa/registro`** → Valida los datos del formulario de registro (anotaciones + validación manual de coincidencia de contraseñas y de nombre de usuario duplicado). Si todo es válido, encripta la contraseña con BCrypt, guarda el usuario y redirige a `/inicio`. Si hay errores, los muestra junto a cada campo correspondiente.
3. **`POST /procesa/login`** → Valida los datos del formulario de login y compara la contraseña ingresada contra el hash guardado usando BCrypt (`matches`). Si es correcto, redirige a `/inicio`. Si no, muestra el error junto al campo de contraseña.
4. **`GET /inicio`** → Muestra `inicio.jsp` con el mensaje de bienvenida.

## Seguridad de contraseñas

Las contraseñas nunca se guardan ni se comparan en texto plano. Se utiliza `BCryptPasswordEncoder`:

- Al registrar: `encriptador.encode(contrasena)` genera el hash que se guarda en base de datos.
- Al hacer login: `encriptador.matches(contrasenaIngresada, hashGuardado)` compara de forma segura sin desencriptar.

## Cómo ejecutar el proyecto

1. Crear la base de datos `login_registro_db` en MySQL.
2. Configurar las credenciales en `application.properties`.
3. Desde STS: click derecho sobre el proyecto → `Run As` → `Spring Boot App`.
4. Abrir el navegador en [http://localhost:8080/](http://localhost:8080/).

## Pruebas realizadas

- Registro con campos vacíos → se muestran los mensajes de validación junto a cada campo.
- Registro con datos válidos → el usuario se guarda en base de datos con la contraseña encriptada (hash BCrypt visible en la columna `contrasena`), y redirige a `/inicio`.
- Login con credenciales correctas → redirige a `/inicio`.
- Login con contraseña incorrecta → muestra el mensaje "Usuario o contraseña incorrectos" junto al campo de contraseña, sin redirigir.

## Notas

- Las columnas `fecha_creacion` y `fecha_actualizacion` se gestionan automáticamente por la aplicación (no por triggers de la base de datos), usando los callbacks `@PrePersist` y `@PreUpdate` de JPA.
- El diseño visual de `index.jsp` e `inicio.jsp` usa un CSS básico, suficiente para cumplir el requerimiento funcional del Core; puede mejorarse estéticamente como extensión opcional.