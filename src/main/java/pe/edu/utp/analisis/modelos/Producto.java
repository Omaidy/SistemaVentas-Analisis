package pe.edu.utp.analisis.modelos;

import java.util.List;

public class Producto {
    private String codigo;
    private List<String> tallas;
    private int cantidad;
    private Double precio;


    public Producto(String codigo, List<String> tallas, int cantidad, Double precio) {
        this.codigo = codigo;
        this.tallas = tallas;
        this.cantidad = cantidad;
        this.precio = precio;

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<String> getTallas() {
        return tallas;
    }

    public void setTallas(List<String> tallas) {
        this.tallas = tallas;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
