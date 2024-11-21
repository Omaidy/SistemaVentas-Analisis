package pe.edu.utp.analisis.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSecu {

    private String url = "jdbc:mariadb://localhost:3306/chifa";
    private String user = "root";
    private String pass = "";

    public ConexionSecu() {

    }

    public Connection conectar() {
        try {
            String driver = "org.mariadb.jdbc.Driver";
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver no encontrado: " + e.getMessage());
            return null;
        }
    }

    public void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al desconectar: " + e.getMessage());
            }
        }
    }
}