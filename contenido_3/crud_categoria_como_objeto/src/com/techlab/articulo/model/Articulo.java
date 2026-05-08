package com.techlab.articulo.model;

/*
 * CLASE ARTICULO
 * --------------------------------------------------
 * Ahora esta clase evoluciona:
 * además de código, nombre y precio,
 * tendrá una categoría de tipo Categoria.
 *
 * Esto es muy importante en POO:
 * una clase puede tener como atributo otra clase.
 */
public class Articulo {

    private int codigo;
    private String nombre;
    private double precio;
    private Categoria categoria;

    /*
     * Constructor completo.
     *
     * Ahora también recibe una categoría.
     */
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

    /*
     * Getter de categoría.
     *
     * Devuelve un objeto Categoria completo.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /*
     * Setter de categoría.
     *
     * Permite reemplazar la categoría actual por otra.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /*
     * toString
     * --------------------------------------------------
     * Mostramos también la categoría del artículo.
     *
     * Como la categoría también tiene su propia clase,
     * podemos acceder a sus atributos con getters.
     */
    @Override
    public String toString() {
        return "Artículo {" +
                "código=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoría='" + categoria.getNombre() + '\'' +
                '}';
    }
}
