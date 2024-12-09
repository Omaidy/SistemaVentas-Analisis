package pe.edu.utp.analisis.facade.impl;

import javafx.scene.control.Alert;
import pe.edu.utp.analisis.facade.InicioFacade;
import pe.edu.utp.analisis.servicios.LoginService;
import pe.edu.utp.analisis.modelos.Usuario;
import pe.edu.utp.analisis.utilidades.ActivityChecker;
import pe.edu.utp.analisis.dao.EmpleadoDAO;
import pe.edu.utp.analisis.dao.impl.EmpleadoDAOImpl;
import pe.edu.utp.analisis.modelos.Empleado;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioFacadeImpl implements InicioFacade {

    private final LoginService loginService;

    public InicioFacadeImpl() {
        this.loginService = new LoginService();

    }

    @Override
    public boolean autenticarUsuario(String usuario, String contraseña) {
        try {
            return loginService.login(usuario, contraseña);
        } catch (Exception e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Usuario obtenerInformacionUsuario(int idEmpleado) {
        try {
            return loginService.obtenerInfoUsuario(idEmpleado);
        } catch (Exception e) {
            System.err.println("Error al obtener información del usuario: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void iniciarVerificacionActividad(int idEmpleado) {
        Thread actividadCheckerThread = new Thread(new ActivityChecker(idEmpleado, loginService));
        actividadCheckerThread.start();
    }

    @Override
    public void logout() {
        loginService.logout();  // Llamamos al método logout del servicio
        System.out.println("Has cerrado sesión exitosamente.");
    }

    @Override
    public void abrirPorCargo(int idEmpleado) {
        try {
            // Obtener el cargo del empleado
            String cargoEmpleado = loginService.obtenerCargo(idEmpleado);

            if (cargoEmpleado == null || cargoEmpleado.isEmpty()) {
                mostrarAlerta("Error", "No se pudo obtener el cargo del empleado.");
                return;
            }

            // Determinar qué vista abrir según el cargo
            if ("vendedor".equalsIgnoreCase(cargoEmpleado)) {
                // Si el cargo es "Vendedor", abrimos Vista-empleado.fxml
                abrirVista("Vista-empleado.fxml");
            } else {
                // Si el cargo no es "Vendedor", abrimos Vista-administrador.fxml
                abrirVista("Vista-administrador.fxml");
            }

        } catch (Exception e) {
            // Imprimir el error en el log y mostrar alerta al usuario
            System.err.println("Error al abrir la vista según el cargo: " + e.getMessage());
            mostrarAlerta("Error", "Hubo un error al intentar abrir la vista.");
        }
    }

    private void abrirVista(String vistaFXML) {
        try {
            // Asegúrate de que la ruta esté correctamente configurada para cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pe/edu/utp/analisis/fxml/" + vistaFXML));
            Parent root = loader.load();

            // Crear y mostrar una nueva escena en una nueva ventana
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema"); // Título de la ventana
            stage.show();

        } catch (IOException e) {
            // Manejar error si la vista no se pudo cargar
            System.err.println("Error al cargar la vista: " + e.getMessage());
            mostrarAlerta("Error", "No se pudo cargar la vista solicitada.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Usamos ERROR en lugar de INFORMATION
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
