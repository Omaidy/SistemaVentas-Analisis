package pe.edu.utp.analisis.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pe.edu.utp.analisis.facade.InicioFacade;
import pe.edu.utp.analisis.facade.impl.InicioFacadeImpl;
import pe.edu.utp.analisis.modelos.SesionUsuario;
import pe.edu.utp.analisis.modelos.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InicioController {

    @FXML
    private TextField txt_usuario;

    @FXML
    private PasswordField txt_contraseña;

    private InicioFacade inicioFacade;

    public InicioController() {
        // Crear una instancia de la implementación del Facade
        this.inicioFacade = new InicioFacadeImpl();
    }

    @FXML
    public void initialize() {
        // Configuraciones de inicialización si son necesarias
    }

    @FXML
    private void handleIniciarSesion(MouseEvent event) {
        String usuario = txt_usuario.getText();
        String contraseña = txt_contraseña.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingrese usuario y contraseña.");
            return;
        }

        if (inicioFacade.autenticarUsuario(usuario, contraseña)) {
            SesionUsuario sesion = SesionUsuario.getInstance();
            int idEmpleado = sesion.getIdEmpleado();

            Usuario usuarioInfo = inicioFacade.obtenerInformacionUsuario(idEmpleado);

            if (usuarioInfo != null) {
                // Mostrar alerta de bienvenida
                mostrarAlerta("Éxito", "Bienvenido " + usuarioInfo.getNombre() + " " + usuarioInfo.getApellido());

                // Verificar actividad en segundo plano
                inicioFacade.iniciarVerificacionActividad(idEmpleado);

                // Llamar a la función que abre la vista correspondiente según el cargo
                inicioFacade.abrirPorCargo(idEmpleado);

                // Ahora cerramos la ventana de inicio de sesión
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo obtener la información del usuario.");
            }
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas o el usuario no está activo.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para cerrar la ventana
    private void cerrarVentana() {
        Stage stage = (Stage) txt_usuario.getScene().getWindow();  // Obtiene la ventana actual
        stage.close();  // Cierra la ventana
    }

    // Método para cerrar la ventana (implementación del método con la anotación @FXML)
    @FXML
    private void handleCerrarVentana(MouseEvent event) {
        cerrarVentana();  // Llama al método que cierra la ventana
    }

    // Método para minimizar la ventana
    @FXML
    private void handleMinimizarVentana(MouseEvent event) {
        Stage stage = (Stage) txt_usuario.getScene().getWindow();
        stage.setIconified(true); // Esto minimiza la ventana
    }
}
