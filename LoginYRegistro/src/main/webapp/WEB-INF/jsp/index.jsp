<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login y Registro</title>
    <style>
        body { font-family: Arial, sans-serif; background:#f4f4f4; }
        .contenedor { display:flex; gap:40px; justify-content:center; padding:30px; flex-wrap:wrap; }
        .caja { background:#fff; padding:25px; border-radius:8px; box-shadow:0 0 8px rgba(0,0,0,0.1); width:350px; }
        .campo { margin-bottom:12px; }
        .campo label { display:block; font-weight:bold; margin-bottom:4px; }
        .campo input { width:100%; padding:6px; box-sizing:border-box; }
        .error { color:red; font-size:0.85em; margin-left:5px; }
        h2 { text-align:center; }
        button { width:100%; padding:8px; margin-top:10px; cursor:pointer; }
    </style>
</head>
<body>

<div class="contenedor">

    <!-- FORMULARIO DE REGISTRO -->
    <div class="caja">
        <h2>Registro</h2>
        <form:form method="POST" action="/procesa/registro" modelAttribute="registroDTO">

            <div class="campo">
                <label>Nombre de usuario</label>
                <form:input path="nombreUsuario" />
                <form:errors path="nombreUsuario" cssClass="error" />
            </div>

            <div class="campo">
                <label>Contraseña</label>
                <form:password path="contrasena" />
                <form:errors path="contrasena" cssClass="error" />
            </div>

            <div class="campo">
                <label>Confirmar contraseña</label>
                <form:password path="confirmacionContrasena" />
                <form:errors path="confirmacionContrasena" cssClass="error" />
            </div>

            <div class="campo">
                <label>Correo</label>
                <form:input path="correo" />
                <form:errors path="correo" cssClass="error" />
            </div>

            <div class="campo">
                <label>Nombre</label>
                <form:input path="nombre" />
                <form:errors path="nombre" cssClass="error" />
            </div>

            <div class="campo">
                <label>Apellido</label>
                <form:input path="apellido" />
                <form:errors path="apellido" cssClass="error" />
            </div>

            <div class="campo">
                <label>Fecha de nacimiento</label>
                <form:input type="date" path="fechaDeNacimiento" />
                <form:errors path="fechaDeNacimiento" cssClass="error" />
            </div>

            <button type="submit">Registrarse</button>
        </form:form>
    </div>

    <!-- FORMULARIO DE LOGIN -->
    <div class="caja">
        <h2>Login</h2>
        <form:form method="POST" action="/procesa/login" modelAttribute="loginDTO">

            <div class="campo">
                <label>Nombre de usuario</label>
                <form:input path="nombreUsuario" />
                <form:errors path="nombreUsuario" cssClass="error" />
            </div>

            <div class="campo">
                <label>Contraseña</label>
                <form:password path="contrasena" />
                <form:errors path="contrasena" cssClass="error" />
            </div>

            <button type="submit">Entrar</button>
        </form:form>
    </div>

</div>

</body>
</html>