package es.dwes.dentista.controlador;

import es.dwes.dentista.modelo.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/citas")
public class CitaServlet extends HttpServlet {
    private CitaDAO citaDAO = new CitaDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private OdontologoDAO odontologoDAO = new OdontologoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        if ("listar".equals(accion)) {
            boolean todas = "1".equals(request.getParameter("todas"));
            List<Cita> lista = citaDAO.obtenerCitas(todas);
            request.setAttribute("citas", lista);
            request.setAttribute("todas", todas);
            request.getRequestDispatcher("/listadoCitas.jsp").forward(request, response);
        } else if ("formulario".equals(accion)) {
            List<Paciente> pacientes = pacienteDAO.obtenerTodos();
            List<Odontologo> odontologos = odontologoDAO.obtenerTodos();
            request.setAttribute("pacientes", pacientes);
            request.setAttribute("odontologos", odontologos);
            request.getRequestDispatcher("/formularioCita.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        try {
            if ("insertar".equals(accion)) {
                Cita c = new Cita();
                c.setFecha(Date.valueOf(request.getParameter("fecha")));
                
                String hora = request.getParameter("hora");
                if (hora != null && hora.length() == 5) {
                    hora = hora + ":00";
                }
                c.setHora(hora);
                
                c.setIdPaciente(Integer.parseInt(request.getParameter("id_paciente")));
                c.setIdOdontologo(Integer.parseInt(request.getParameter("id_odontologo")));
                c.setMotivo(request.getParameter("motivo"));

                citaDAO.insertar(c);
                response.sendRedirect("citas?accion=listar");

            } else if ("borrar".equals(accion)) {
                int idCita = Integer.parseInt(request.getParameter("id_cita"));
                citaDAO.borrar(idCita);
                response.sendRedirect("citas?accion=listar");
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/listadoCitas.jsp").forward(request, response);
        }
    }
}