package pe.edu.utp.analisis.dao;

import pe.edu.utp.analisis.modelos.SesionUsuario;

import java.sql.SQLException;
import java.util.List;

public interface SesionDAO {

    // Métodos para manejar las sesiones


    int guardarSesion(int idEmpleado) throws SQLException;

    void cerrarSesion(int idEmpleado, long duracion) throws SQLException;


}
