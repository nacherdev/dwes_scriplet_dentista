package es.dwes.dentista.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    public List<Cita> obtenerCitas(boolean todas) {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.fecha, c.hora, c.motivo, "
                   + "p.id_paciente, p.nombre, p.apellidos, "
                   + "o.nombre AS odontologo "
                   + "FROM cita c "
                   + "JOIN paciente p ON p.id_paciente = c.id_paciente "
                   + "JOIN odontologos o ON o.id_odontologo = c.id_odontologo "
                   + (todas ? "" : "WHERE c.fecha >= CURDATE() ")
                   + "ORDER BY c.fecha, c.hora";

        try (Connection conexion = ConexionBD.conectar();
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Cita c = new Cita();
                c.setIdCita(rs.getInt("id_cita"));
                c.setFecha(rs.getDate("fecha"));
                c.setHora(rs.getString("hora"));
                c.setMotivo(rs.getString("motivo"));
                c.setIdPaciente(rs.getInt("id_paciente"));
                c.setNombrePaciente(rs.getString("nombre"));
                c.setApellidosPaciente(rs.getString("apellidos"));
                c.setNombreOdontologo(rs.getString("odontologo"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void insertar(Cita c) throws SQLException {
        String sql = "INSERT INTO cita (fecha, hora, id_paciente, id_odontologo, motivo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(c.getFecha().getTime()));
            ps.setTime(2, java.sql.Time.valueOf(c.getHora()));
            ps.setInt(3, c.getIdPaciente());
            ps.setInt(4, c.getIdOdontologo());
            ps.setString(5, c.getMotivo());
            ps.executeUpdate();
        }
    }

    public void borrar(int idCita) throws SQLException {
        String sql = "DELETE FROM cita WHERE id_cita = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            ps.executeUpdate();
        }
    }
}