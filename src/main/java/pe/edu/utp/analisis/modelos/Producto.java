package pe.edu.utp.analisis.modelos;

import java.util.List;

public class Producto {
    private int id;
    private String nombre;
    private String talla;
    private int stock;
    private double precio;
    private String categoria;
    private String descripcion;

    // Constructor
    public Producto(int id, String nombre, String talla, int stock, double precio, String categoria, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.talla = talla;
        this.stock = stock;
        this.precio = precio;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
