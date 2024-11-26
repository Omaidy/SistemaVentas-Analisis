package pe.edu.utp.analisis.utilidades;

import pe.edu.utp.analisis.servicios.LoginService;
import pe.edu.utp.analisis.modelos.Usuario;

import java.sql.SQLException;

public class ActivityChecker implements Runnable {

    private final int idEmpleado;
    private final LoginService loginService;

    public ActivityChecker(int idEmpleado, LoginService loginService) {
        this.idEmpleado = idEmpleado;
        this.loginService = loginService;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Verificar la actividad del usuario cada 5 segundos
                Usuario usuarioInfo = loginService.obtenerInfoUsuario(idEmpleado);
                if (usuarioInfo != null && !usuarioInfo.getActividad()) {
                    System.out.println("Tu cuenta está inactiva. Cerrando sesión...");
                    loginService.logout();  // Cerrar sesión
                    System.exit(0);  // Terminar la aplicación
                }
                Thread.sleep(5000);  // Esperar 5 segundos antes de la siguiente verificación
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
