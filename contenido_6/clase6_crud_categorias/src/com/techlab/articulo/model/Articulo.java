package com.techlab.articulo.model;

import com.techlab.articulo.interfaces.Calculable;

/*
 * CLASE ABSTRACTA ARTICULO
 * --------------------------------------------------
 * Sigue concentrando lo común a todos los artículos.
 */
public abstract class Articulo implements Calculable {

    private int codigo;
    private String nombre;
    private double precio;
    private Categoria categoria;

    public Articulo(int codigo, String nombre, double precio, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public abstract String getTipoArticulo();

    public abstract String getDetalleEspecifico();

    @Override
    public String toString() {
        return "Artículo {" +
                "código=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio base=" + precio +
                ", categoría='" + categoria.getNombre() + '\'' +
                ", tipo='" + getTipoArticulo() + '\'' +
                ", detalle='" + getDetalleEspecifico() + '\'' +
                ", precio final=" + calcularPrecioFinal() +
                '}';
    }
}
