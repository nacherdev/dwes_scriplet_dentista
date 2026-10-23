package es.dwes.dentista.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAO {
    public List<Odontologo> obtenerTodos() {
        List<Odontologo> lista = new ArrayList<>();
        String sql = "SELECT id_odontologo, nombre, especialidad FROM odontologos ORDER BY nombre";
        try (Connection conexion = ConexionBD.conectar();
             Statement s = conexion.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Odontologo o = new Odontologo();
                o.setIdOdontologo(rs.getInt("id_odontologo"));
                o.setNombre(rs.getString("nombre"));
                o.setEspecialidad(rs.getString("especialidad"));
                lista.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}