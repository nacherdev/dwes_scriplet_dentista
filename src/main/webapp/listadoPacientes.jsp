<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado de pacientes</title>
    <link rel="stylesheet" type="text/css" href="estilos.css" />
  </head>
  <body>
    <h1>Listado de pacientes</h1>
    
    <c:if test="${not empty error}">
      <p class="error">Error: ${error}</p>
    </c:if>

    <table>
      <tr>
        <th>Id</th>
        <th>Nombre</th>
        <th>Apellidos</th>
        <th>DNI</th>
        <th>Fecha nacimiento</th>
        <th>Teléfono</th>
        <th>Email</th>
        <th>Dirección</th>
        <th>Alergias</th>
        <th>Citas</th>
        <th>Acciones</th>
      </tr>
      <c:forEach var="p" items="${pacientes}">
        <tr>
          <td>${p.idPaciente}</td>
          <td>${p.nombre}</td>
          <td>${p.apellidos}</td>
          <td>${p.dni}</td>
          <td>${p.fechaNacimiento}</td>
          <td>${p.telefono}</td>
          <td>${p.email}</td>
          <td>${p.direccion}</td>
          <td>${p.alergias}</td>
          <td>${p.numCitas}</td>
          <td>
            <a href="citas?accion=formulario&paciente=${p.idPaciente}">Nueva cita</a>
            <form method="post" action="pacientes?accion=borrar" style="display:inline;">
              <input type="hidden" name="id_paciente" value="${p.idPaciente}"/>
              <input type="submit" value="borrar" onclick="return confirm('¿Seguro que deseas borrar este paciente? Se borrarán sus citas asociadas.');"/>
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
    <p><a href="index.jsp">Volver al inicio</a></p>
  </body>
</html>