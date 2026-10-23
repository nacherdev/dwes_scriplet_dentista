package es.dwes.dentista.controlador;

import es.dwes.dentista.modelo.Paciente;
import es.dwes.dentista.modelo.PacienteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/pacientes")
public class PacienteServlet extends HttpServlet {
    private PacienteDAO pacienteDAO = new PacienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        if ("listar".equals(accion)) {
            List<Paciente> lista = pacienteDAO.obtenerTodos();
            request.setAttribute("pacientes", lista);
            request.getRequestDispatcher("/listadoPacientes.jsp").forward(request, response);
        } else if ("formulario".equals(accion)) {
            request.getRequestDispatcher("/formularioPaciente.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        try {
            if ("insertar".equals(accion)) {
                Paciente p = new Paciente();
                p.setNombre(request.getParameter("nombre"));
                p.setApellidos(request.getParameter("apellidos"));
                p.setDni(request.getParameter("dni"));
                
                String fechaNac = request.getParameter("fecha_nacimiento");
                if (fechaNac != null && !fechaNac.isBlank()) {
                    p.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(fechaNac));
                }
                
                p.setTelefono(request.getParameter("telefono"));
                p.setEmail(request.getParameter("email"));
                p.setDireccion(request.getParameter("direccion"));
                p.setAlergias(request.getParameter("alergias"));

                pacienteDAO.insertar(p);
                response.sendRedirect("pacientes?accion=listar");

            } else if ("borrar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id_paciente"));
                pacienteDAO.borrar(id);
                response.sendRedirect("pacientes?accion=listar");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/listadoPacientes.jsp").forward(request, response);
        }
    }
}