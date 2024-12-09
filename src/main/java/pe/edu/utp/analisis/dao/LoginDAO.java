package pe.edu.utp.analisis.dao;

import pe.edu.utp.analisis.modelos.Login;
import java.util.List;

public interface LoginDAO {
    void create(Login login);
    Login read(int id);
    void update(Login login);
    List<Login> readAll();
    void delete(int id);
    int obtenerIdEmpleadoPorId(int id);
}
