package pe.edu.utp.analisis.dao.impl;

import pe.edu.utp.analisis.dao.SesionDAO;
import pe.edu.utp.analisis.utilidades.ConexionSecu;
import java.sql.*;
import java.util.logging.Logger;

public class SesionDAOImpl implements SesionDAO {
    private static final Logger logger = Logger.getLogger(ProductoDAOImpl.class.getName());
    private ConexionSecu conexionSecu;

    public SesionDAOImpl() {
        conexionSecu = new ConexionSecu();
    }

    // Método para guardar la sesión al iniciar
    @Override
    public int guardarSesion(int idEmpleado) {
        String sql = "INSERT INTO sesiones (id_empleado, fecha_inicio, fecha_cierre, duracion) VALUES (?, NOW(), NULL, NULL)";
        try (Connection conn = conexionSecu.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idEmpleado);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);  // Devolver el id_sesion generado
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // En caso de error, devolver -1
    }

    // Método para cerrar la sesión al terminar
    @Override
    public void cerrarSesion(int idSesion, long duracion) {
        String sql = "UPDATE sesiones SET fecha_cierre = NOW(), duracion = ? WHERE id_sesion = ?";
        try (Connection conn = conexionSecu.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, duracion);  // Establecer la duración en minutos
            stmt.setInt(2, idSesion);   // Establecer el id_sesion para actualizar
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Sesión con ID " + idSesion + " cerrada exitosamente.");
            } else {
                System.out.println("No se pudo cerrar la sesión con ID " + idSesion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
