package pe.edu.utp.analisis.dao.impl;

import pe.edu.utp.analisis.algoritmos.GeneradorUsuario;
import pe.edu.utp.analisis.dao.EmpleadoDAO;
import pe.edu.utp.analisis.modelos.Empleado;
import pe.edu.utp.analisis.utilidades.ConexionSecu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    private ConexionSecu conexion;

    public EmpleadoDAOImpl(ConexionSecu conexion) {
        this.conexion = conexion;
    }

    @Override
    public void crearEmpleado(Empleado empleado) {
        // Generar el nombre de usuario utilizando el algoritmo
        String nombreUsuarioGenerado = GeneradorUsuario.generarNombreUsuario(empleado);

        // Establecer el nombre de usuario generado en el objeto empleado
        empleado.setNombreUsuario(nombreUsuarioGenerado);

        // SQL para insertar un nuevo usuario en la base de datos
        String sql = "INSERT INTO usuarios (id_usuario, nombre, apellido, nombreUsuario, contrasena, cargo, actividad) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empleado.getId_usuario());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellido());
            stmt.setString(4, empleado.getNombreUsuario());  // Usar el nombre de usuario generado
            stmt.setString(5, empleado.getContrasena());
            stmt.setString(6, empleado.getCargo());
            stmt.setBoolean(7, empleado.getActividad());

            // Ejecutar la consulta de inserción
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Empleado leerEmpleado(int id) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Empleado(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("nombreUsuario"),
                        rs.getString("contrasena"),
                        rs.getString("cargo"),
                        rs.getBoolean("actividad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No se encontró
    }

    @Override
    public List<Empleado> leerTodosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE cargo != 'administrador'";
        try (Connection conn = conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                empleados.add(new Empleado(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("nombreUsuario"),
                        rs.getString("contrasena"),
                        rs.getString("cargo"),
                        rs.getBoolean("actividad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, nombreUsuario = ?, cargo = ?, actividad = ? WHERE id_usuario = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getNombreUsuario());
            stmt.setString(4, empleado.getCargo());
            stmt.setBoolean(5, empleado.getActividad());
            stmt.setInt(6, empleado.getId_usuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarEmpleado(int id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void desconectarEmpleado(int id) {
        String sql = "UPDATE usuarios SET actividad = ? WHERE id_usuario = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, false); // Cambiar a inactivo
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int obtenerUltimoId() {
        String sql = "SELECT MAX(id_usuario) AS max_id FROM usuarios";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Si no se encuentra, devolvemos 0
    }

}