package pe.edu.utp.analisis.dao;

import pe.edu.utp.analisis.modelos.Producto;

import java.util.List;

public interface ProductoDAO {
    void insertarProducto(Producto producto);
    List<Producto> obtenerTodosProductos();
    Producto obtenerProducto(String nombre, String talla);
    void actualizarStock(int id, int nuevoStock);
    void eliminarProducto(int id);
    Producto obtenerProductoPorId(int id);
}
