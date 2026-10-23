<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Clínica Dental</title>
    <link rel="stylesheet" type="text/css" href="estilos.css" />
  </head>
  <body>
    <h1>Bienvenido a la aplicación de gestión de citas de la clínica dental</h1>
    <p>Seleccione una opción:</p>
    <ul>
      <li><a href="pacientes?accion=listar">Ver listado de pacientes</a></li>
      <li><a href="citas?accion=listar">Ver próximas citas</a></li>
      <li><a href="citas?accion=formulario">Nueva cita</a></li>
      <li><a href="pacientes?accion=formulario">Nuevo paciente</a></li>
    </ul>
  </body>
</html>