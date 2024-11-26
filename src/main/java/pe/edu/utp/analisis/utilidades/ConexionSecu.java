package pe.edu.utp.analisis.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSecu {

    private String url = "jdbc:mariadb://localhost:3306/sistemaV";
    private String user = "root";
    private String pass = "";

    // Constructor vacío
    public ConexionSecu() {
    }

    // Método para conectar a la base de datos
    public  Connection conectar() {
         String url = "jdbc:mariadb://localhost:3306/sistemaV";
         String user = "root";
         String pass = "";

        try {
            String driver = "org.mariadb.jdbc.Driver";
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    // Método para cerrar la conexión
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
