package pe.edu.utp.analisis.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pe.edu.utp.analisis.utilidades.ConexionSecu;

import java.sql.Connection;

public class HelloController {

    @FXML
    private Button btnTestConnection;

    @FXML
    private void handleTestConnection(ActionEvent event) {
        // Crear una instancia de ConexionSecu
        ConexionSecu conexion = new ConexionSecu();

        // Intentar la conexión
        try (Connection conn = conexion.conectar()) {
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos.");
                // Si la conexión es exitosa, cargar el archivo inicio.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/analisis/fxml/Vista-inicio.fxml"));
                Scene scene = new Scene(loader.load(), 666, 455);
                Stage stage = (Stage) btnTestConnection.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Login");
                stage.show();
            } else {
                System.out.println("La conexión ha fallado.");
                // Puedes mostrar un mensaje en la interfaz gráfica si la conexión falla
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar la conexión: " + e.getMessage());
        }
    }
}
