package pe.edu.utp.analisis.dao.impl;

import pe.edu.utp.analisis.dao.VentaDAO;
import pe.edu.utp.analisis.modelos.Venta;
import pe.edu.utp.analisis.modelos.DetalleVenta;
import pe.edu.utp.analisis.utilidades.ConexionSecu;

import java.sql.*;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {

    private ConexionSecu conexionSecu;

    public VentaDAOImpl() {
        conexionSecu = new ConexionSecu();
    }

    @Override
    public void guardarVenta(Venta venta) {
        String queryVenta = "INSERT INTO ventas (fecha, total, id_cliente, id_empleado) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexionSecu.conectar();
             PreparedStatement ps = conn.prepareStatement(queryVenta, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, new java.sql.Timestamp(venta.getFecha().getTime()));  // Convertir Date a Timestamp
            ps.setDouble(2, venta.getTotal());
            ps.setInt(3, venta.getCliente().getDni());  // Usar el DNI del Cliente
            ps.setInt(4, venta.getEmpleado().getId_empleado());  // Empleado asociado

            // Ejecutar la consulta de inserción
            ps.executeUpdate();

            // Obtener el ID generado de la venta
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idVenta = rs.getInt(1);  // Obtener el ID generado automáticamente
                    venta.setIdVenta(idVenta);   // Establecer el ID de la venta en el objeto Venta
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardarDetallesVenta(List<DetalleVenta> detallesVenta) {
        String queryDetalle = "INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexionSecu.conectar(); PreparedStatement ps = conn.prepareStatement(queryDetalle)) {
            for (DetalleVenta detalle : detallesVenta) {
                // Insertar el detalle de venta usando el ID de la venta asociada
                ps.setInt(1, detalle.getVenta().getIdVenta());  // Obtener el ID de la venta
                ps.setInt(2, detalle.getProducto().getId());  // ID del producto
                ps.setInt(3, detalle.getCantidad());  // Cantidad
                ps.setDouble(4, detalle.getPrecioUnitario());  // Precio unitario

                ps.addBatch();  // Añadir al lote
            }

            // Ejecutar el lote de inserciones
            ps.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
