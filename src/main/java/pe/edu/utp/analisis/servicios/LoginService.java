package pe.edu.utp.analisis.servicios;

import pe.edu.utp.analisis.modelos.SesionUsuario;
import pe.edu.utp.analisis.dao.impl.SesionDAOImpl;
import pe.edu.utp.analisis.modelos.Usuario;
import pe.edu.utp.analisis.utilidades.ConexionSecu;

import java.sql.*;

public class LoginService {
    private SesionDAOImpl sesionDAO;
    private ConexionSecu conexionSecu;

    public LoginService() {
        sesionDAO = new SesionDAOImpl();
        conexionSecu = new ConexionSecu();
    }

    // Iniciar sesión
    public boolean login(String usuario, String contrasena) {
        String sql = "SELECT id_empleado, usuario, contrasena, actividad FROM login WHERE usuario = ?";

        try (Connection conn = conexionSecu.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idEmpleado = rs.getInt("id_empleado");
                    String contrasenaEnBaseDeDatos = rs.getString("contrasena");
                    boolean actividad = rs.getBoolean("actividad");

                    // Validamos las credenciales
                    if (contrasena.equals(contrasenaEnBaseDeDatos)) {
                        // Verificamos si el usuario ya tiene una sesión activa
                        if (actividad) {
                            System.out.println("El usuario ya tiene una sesión activa.");
                            return false;
                        }

                        // Si las credenciales son correctas y el usuario no tiene una sesión activa
                        // Crear una nueva sesión
                        int idSesion = sesionDAO.guardarSesion(idEmpleado);  // Crear una nueva sesión en la base de datos
                        if (idSesion != -1) {
                            // Crear la instancia de SesionUsuario y almacenar el id_sesion
                            SesionUsuario sesion = SesionUsuario.getInstance();
                            sesion.iniciarSesion(idEmpleado, usuario, idSesion);

                            // Actualizamos la actividad a 'true' (usuario ahora tiene sesión activa)
                            actualizarActividad(idEmpleado, true);

                            System.out.println("Sesión iniciada correctamente.");
                            return true;
                        }
                    } else {
                        System.out.println("Contraseña incorrecta.");
                    }
                } else {
                    System.out.println("Usuario no encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cerrar sesión
    public void logout() {
        SesionUsuario sesion = SesionUsuario.getInstance();
        if (sesion.isSesionActiva()) {
            // Cerrar la sesión
            sesion.cerrarSesion();

            // Obtener la duración de la sesión en segundos
            long duracion = sesion.getDuracion();
            sesionDAO.cerrarSesion(sesion.getIdSesion(), duracion);  // Actualizar la sesión en la base de datos

            // Actualizamos la actividad a 'false' (usuario ya no tiene sesión activa)
            actualizarActividad(sesion.getIdEmpleado(), false);

            System.out.println("Sesión cerrada con éxito. Duración: " + duracion + " segundos.");
        }
    }

    // Método para actualizar el campo 'actividad' en la base de datos
    private void actualizarActividad(int idEmpleado, boolean actividad) {
        String sql = "UPDATE login SET actividad = ? WHERE id_empleado = ?";

        try (Connection conn = conexionSecu.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, actividad);
            stmt.setInt(2, idEmpleado);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Actividad del usuario actualizada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String obtenerCargo(int idEmpleado) {
        String sql = "SELECT cargo FROM empleados WHERE id_empleado = ?"; // Asumiendo que la tabla es 'empleados'

        try (Connection conn = conexionSecu.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("cargo"); // Retorna el cargo del empleado
                } else {
                    System.out.println("Empleado no encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra el empleado o si hay un error
    }
    public Usuario obtenerInfoUsuario(int idEmpleado) {
        // Primero obtenemos los datos del empleado de la tabla 'empleados'
        String sqlEmpleados = "SELECT nombre, apellido, cargo FROM empleados WHERE id_empleado = ?";

        String nombre = "";
        String apellido = "";
        String cargo = "";

        try (Connection conn = conexionSecu.conectar();
             PreparedStatement stmt = conn.prepareStatement(sqlEmpleados)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("nombre");
                    apellido = rs.getString("apellido");
                    cargo = rs.getString("cargo");
                } else {
                    System.out.println("Empleado no encontrado.");
                    return null;  // Si no se encuentra el empleado, retornar null.
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // En caso de error de base de datos, retornar null.
        }

        // Luego obtenemos la información del usuario de la tabla 'login'
        String sqlLogin = "SELECT id, actividad FROM login WHERE id_empleado = ?";
        int idUsuario = -1;
        boolean actividad = false;

        try (Connection conn = conexionSecu.conectar();
             PreparedStatement stmt = conn.prepareStatement(sqlLogin)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idUsuario = rs.getInt("id");
                    actividad = rs.getBoolean("actividad");
                } else {
                    System.out.println("Login no encontrado para este empleado.");
                    return null;  // Si no se encuentra el usuario, retornar null.
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // En caso de error de base de datos, retornar null.
        }

        // Finalmente, creamos y retornamos el objeto Usuario
        return new Usuario(idUsuario, nombre, apellido, nombre, "", cargo, actividad);
    }

}
