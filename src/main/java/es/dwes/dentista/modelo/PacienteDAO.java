package es.dwes.dentista.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public List<Paciente> obtenerTodos() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT p.id_paciente, p.nombre, p.apellidos, p.dni, p.fecha_nacimiento, "
                   + "p.telefono, p.email, p.direccion, p.alergias, "
                   + "(SELECT COUNT(*) FROM cita c WHERE c.id_paciente = p.id_paciente) AS num_citas "
                   + "FROM paciente p ORDER BY p.apellidos, p.nombre";

        try (Connection conexion = ConexionBD.conectar();
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt("id_paciente"));
                p.setNombre(rs.getString("nombre"));
                p.setApellidos(rs.getString("apellidos"));
                p.setDni(rs.getString("dni"));
                p.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                p.setDireccion(rs.getString("direccion"));
                p.setAlergias(rs.getString("alergias"));
                p.setNumCitas(rs.getInt("num_citas"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void insertar(Paciente p) throws SQLException {
        String sql = "INSERT INTO paciente (nombre, apellidos, dni, fecha_nacimiento, telefono, email, direccion, alergias) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDni());
            
            if (p.getFechaNacimiento() == null) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, new java.sql.Date(p.getFechaNacimiento().getTime()));
            }
            
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getEmail());
            ps.setString(7, p.getDireccion());
            ps.setString(8, p.getAlergias());

            ps.executeUpdate();
        }
    }

    public void borrar(int idPaciente) throws SQLException {
        String sql = "DELETE FROM paciente WHERE id_paciente = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ps.executeUpdate();
        }
    }
}