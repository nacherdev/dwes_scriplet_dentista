<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado de citas</title>
    <link rel="stylesheet" type="text/css" href="estilos.css" />
  </head>
  <body>
    <h1>${todas ? "Todas las citas" : "Próximas citas"}</h1>
    
    <c:if test="${not empty error}">
      <p class="error">Error: ${error}</p>
    </c:if>

    <table>
      <tr>
        <th>Id cita</th>
        <th>Fecha</th>
        <th>Hora</th>
        <th>Paciente</th>
        <th>Motivo</th>
        <th>Odontólogo</th>
        <th>Acciones</th>
      </tr>
      <c:forEach var="c" items="${citas}">
        <tr>
          <td>${c.idCita}</td>
          <td>${c.fecha}</td>
          <td>${c.hora}</td>
          <td>${c.apellidosPaciente}, ${c.nombrePaciente}</td>
          <td>${c.motivo}</td>
          <td>${c.nombreOdontologo}</td>
          <td>
            <form method="post" action="citas?accion=borrar">
              <input type="hidden" name="id_cita" value="${c.idCita}"/>
              <input type="submit" value="anular" onclick="return confirm('¿Estás seguro de anular esta cita?');"/>
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
    <p>
      <c:choose>
        <c:when test="${not todas}">
          <a href="citas?accion=listar&todas=1">Ver el histórico completo</a> |
        </c:when>
        <c:otherwise>
          <a href="citas?accion=listar">Ver sólo las próximas citas</a> |
        </c:otherwise>
      </c:choose>
      <a href="citas?accion=formulario">Nueva cita</a> |
      <a href="index.jsp">Volver al inicio</a>
    </p>
  </body>
</html>