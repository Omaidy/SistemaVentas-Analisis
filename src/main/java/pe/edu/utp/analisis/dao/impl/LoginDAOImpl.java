package pe.edu.utp.analisis.dao.impl;

import pe.edu.utp.analisis.dao.LoginDAO;
import pe.edu.utp.analisis.modelos.Login;
import pe.edu.utp.analisis.utilidades.ConexionSecu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {

    private ConexionSecu conexionSecu;

    public LoginDAOImpl() {
        // Initialize ConexionSecu to handle connections
        this.conexionSecu = new ConexionSecu();
    }

    @Override
    public void create(Login login) {
        String sql = "INSERT INTO login (id_empleado, usuario, contrasena, actividad, fecha_creacion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexionSecu.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, login.getId_empleado());
            stmt.setString(2, login.getUsuario());
            stmt.setString(3, login.getContrasena());
            stmt.setInt(4, login.getActividad());
            stmt.setTimestamp(5, Timestamp.valueOf(login.getFecha_creacion()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Login read(int id) {
        Login login = null;
        String sql = "SELECT * FROM login WHERE id = ?";
        try (Connection conn = conexionSecu.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                login = new Login(
                        rs.getInt("id"),
                        rs.getInt("id_empleado"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getInt("actividad"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return login;
    }

    @Override
    public List<Login> readAll() {
        List<Login> logins = new ArrayList<>();
        String sql = "SELECT * FROM login";
        try (Connection conn = conexionSecu.conectar(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                logins.add(new Login(
                        rs.getInt("id"),
                        rs.getInt("id_empleado"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getInt("actividad"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logins;
    }

    @Override
    public void update(Login login) {
        String sql = "UPDATE login SET id_empleado = ?, usuario = ?, contrasena = ?, actividad = ?, fecha_creacion = ? WHERE id = ?";
        try (Connection conn = conexionSecu.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, login.getId_empleado());
            stmt.setString(2, login.getUsuario());
            stmt.setString(3, login.getContrasena());
            stmt.setInt(4, login.getActividad());
            stmt.setTimestamp(5, Timestamp.valueOf(login.getFecha_creacion()));
            stmt.setInt(6, login.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM login WHERE id = ?";
        try (Connection conn = conexionSecu.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int obtenerIdEmpleadoPorId(int id) {
        int idEmpleado = -1;  // Default value in case no result is found
        String sql = "SELECT id_empleado FROM login WHERE id = ?";
        try (Connection conn = conexionSecu.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idEmpleado = rs.getInt("id_empleado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idEmpleado;
    }
}
