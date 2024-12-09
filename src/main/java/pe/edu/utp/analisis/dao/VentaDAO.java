package pe.edu.utp.analisis.dao;

import pe.edu.utp.analisis.modelos.Venta;
import pe.edu.utp.analisis.modelos.DetalleVenta;

import java.util.List;

public interface VentaDAO {

    // Método para guardar una venta
    void guardarVenta(Venta venta);

    // Método para guardar los detalles de una venta
    void guardarDetallesVenta(List<DetalleVenta> detallesVenta);

    // Puedes agregar otros métodos si es necesario (como obtener ventas por cliente, etc.)
}
