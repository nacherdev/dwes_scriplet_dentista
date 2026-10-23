<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Nueva cita</title>
    <link rel="stylesheet" type="text/css" href="estilos.css" />
  </head>
  <body>
    <h2>Introduzca los datos de la nueva cita:</h2>
    <form method="post" action="citas?accion=insertar">
      Fecha <input type="date" name="fecha" required/><br/>
      Hora <input type="time" name="hora" required/><br/>
      Paciente
      <select name="id_paciente" required>
        <option value="">-- Seleccione un paciente --</option>
        <c:forEach var="p" items="${pacientes}">
          <option value="${p.idPaciente}" ${param.paciente == p.idPaciente ? 'selected' : ''}>
            ${p.apellidos}, ${p.nombre}
          </option>
        </c:forEach>
      </select><br/>
      Odontólogo
      <select name="id_odontologo" required>
        <option value="">-- Seleccione un odontólogo --</option>
        <c:forEach var="o" items="${odontologos}">
          <option value="${o.idOdontologo}">${o.nombre} (${o.especialidad})</option>
        </c:forEach>
      </select><br/>
      Motivo <input type="text" name="motivo"/><br/>
      <input type="submit" value="Aceptar">
    </form>
    <p><a href="index.jsp">Volver al inicio</a></p>
  </body>
</html>