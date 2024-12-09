package pe.edu.utp.analisis.estructuras;

import pe.edu.utp.analisis.modelos.Producto;


public class ProductoDTO {
    private Producto producto;  // El producto real
    private int cantidad;  // Cantidad del producto que el usuario desea agregar

    // Constructor
    public ProductoDTO(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

