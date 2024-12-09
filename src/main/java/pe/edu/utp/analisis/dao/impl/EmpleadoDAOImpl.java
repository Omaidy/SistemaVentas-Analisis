package pe.edu.utp.analisis.dao.impl;

import pe.edu.utp.analisis.dao.EmpleadoDAO;
import pe.edu.utp.analisis.dao.LoginDAO;
import pe.edu.utp.analisis.modelos.Empleado;
import pe.edu.utp.analisis.modelos.Login;
import pe.edu.utp.analisis.utilidades.ConexionSecu;
import pe.edu.utp.analisis.algoritmos.GeneradorUsuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    private ConexionSecu conexion;
    private LoginDAO loginDAO;  // Declare LoginDAO

    // Constructor where you can inject both ConexionSecu and LoginDAO
    public EmpleadoDAOImpl(ConexionSecu conexion, LoginDAO loginDAO) {
        this.conexion = conexion;
        this.loginDAO = loginDAO;  // Initialize the LoginDAO
    }

    @Override
    public void crearEmpleado(Empleado empleado) {
        // Primero, crear el registro en la tabla de empleados
        String sqlEmpleado = "INSERT INTO empleados (id_empleado, nombre, apellido, cargo, fecha_creacion) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sqlEmpleado)) {
            stmt.setInt(1, empleado.getId_empleado());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellido());
            stmt.setString(4, empleado.getCargo());
            stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));  // Establecer la fecha de creación

            // Ejecutar la consulta de inserción
            stmt.executeUpdate();

            // Luego, generar el nombre de usuario y crear el registro en la tabla de login
            String nombreUsuarioGenerado = GeneradorUsuario.generarNombreUsuario(empleado);

            // Asignar la contraseña
            String contrasena = "12345678";  // Puedes generar o asignar una contraseña aquí.

            // Obtener la fecha y hora actuales (LocalDateTime)
            LocalDateTime fechaCreacion = LocalDateTime.now();

            // El ID de Login podría ser generado automáticamente por la base de datos (si es autoincrementable),
            // pero si necesitas asignarlo manualmente, puedes utilizar un valor específico o un generador.
            int idLogin = 0;  // Set a value for `id`, assuming it's auto-generated in the DB

            // Crear el objeto Login
            Login nuevoLogin = new Login(idLogin, empleado.getId_empleado(), nombreUsuarioGenerado, contrasena, 0, fechaCreacion);

            // Ingresar el login en la base de datos
            loginDAO.create(nuevoLogin);

        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or rethrowing this exception as a custom exception
        }
    }


    @Override
    public Empleado leerEmpleado(int id) {
        String sql = "SELECT * FROM empleados WHERE id_empleado = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cargo"),
                        rs.getDate("fecha_creacion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or rethrowing this exception as a custom exception
        }
        return null; // No se encontró
    }

    @Override
    public List<Empleado> leerTodosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (Connection conn = conexion.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                empleados.add(new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cargo"),
                        rs.getDate("fecha_creacion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or rethrowing this exception as a custom exception
        }
        return empleados;
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, apellido = ?, cargo = ?, fecha_creacion = ? WHERE id_empleado = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getCargo());
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));  // Actualizar fecha de creación
            stmt.setInt(5, empleado.getId_empleado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or rethrowing this exception as a custom exception
        }
    }

    @Override
    public void eliminarEmpleado(int id) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or rethrowing this exception as a custom exception
        }
    }

    @Override
    public void desconectarEmpleado(int id) {
        String sql = "UPDATE empleados SET actividad = ? WHERE id_empleado = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, false); // Cambiar a inactivo
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or rethrowing this exception as a custom exception
        }
    }

    @Override
    public int obtenerUltimoId() {
        String sql = "SELECT MAX(id_empleado) AS max_id FROM empleados";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or rethrowing this exception as a custom exception
        }
        return 0; // Si no se encuentra, devolvemos 0
    }

    @Override
    public String obtenerCargoEmpleado(int id) {
        String sql = "SELECT cargo FROM empleados WHERE id_empleado = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("cargo"); // Devolver el cargo del empleado
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or rethrowing this exception as a custom exception
        }
        return null; // Si no se encuentra el empleado, devolver null
    }
}
