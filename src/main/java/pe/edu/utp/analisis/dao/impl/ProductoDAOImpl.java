package pe.edu.utp.analisis.dao.impl;

import pe.edu.utp.analisis.dao.ProductoDAO;
import pe.edu.utp.analisis.modelos.Producto;
import pe.edu.utp.analisis.utilidades.ConexionSecu;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductoDAOImpl implements ProductoDAO {
    private static final Logger logger = Logger.getLogger(ProductoDAOImpl.class.getName());
    private ConexionSecu conexion;  // Instancia de ConexionSecu

    public ProductoDAOImpl(ConexionSecu conexion) {
        this.conexion = conexion;
    }


    @Override
    public void insertarProducto(Producto producto) {
        String query = "INSERT INTO productos (nombre, talla, stock, precio, categoria, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getTalla());
            stmt.setInt(3, producto.getStock());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setString(5, producto.getCategoria());
            stmt.setString(6, producto.getDescripcion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Producto> obtenerTodosProductos() {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        try (Connection conn = conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("talla"),
                        rs.getInt("stock"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getString("descripcion")
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public void actualizarStock(int id, int nuevoStock) {
        String query = "UPDATE productos SET stock = ? WHERE id = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarProducto(int id) {
        String query = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Producto obtenerProductoPorId(int id) {
        String query = "SELECT * FROM productos WHERE id = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("talla"),
                        rs.getInt("stock"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Si no se encuentra el producto
    }

    @Override
    public Producto obtenerProducto(String nombre, String talla) {
        String query = "SELECT * FROM productos WHERE nombre = ? AND talla = ?";
        try (Connection conn = conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, talla);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("talla"),
                        rs.getInt("stock"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el producto
    }
}
