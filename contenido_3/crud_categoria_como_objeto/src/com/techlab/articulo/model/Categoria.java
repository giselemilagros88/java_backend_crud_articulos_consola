package com.techlab.articulo.model;

/*
 * CLASE CATEGORIA
 * --------------------------------------------------
 * Esta clase representa una categoría del sistema.
 *
 * En esta etapa todavía no tiene CRUD propio.
 * Solo se usa para:
 * - crear categorías precargadas
 * - asignárselas a los artículos
 *
 * Más adelante esta clase tendrá mucha más importancia
 * cuando hagamos el CRUD de categorías.
 */
public class Categoria {

    private int codigo;
    private String nombre;
    private String descripcion;

    /*
     * Constructor completo.
     *
     * Permite crear categorías listas para usar.
     */
    public Categoria(int codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /*
     * toString
     * --------------------------------------------------
     * Mostramos la categoría de forma clara para que el usuario
     * pueda elegirla por código.
     */
    @Override
    public String toString() {
        return "Categoría {" +
                "código=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", descripción='" + descripcion + '\'' +
                '}';
    }
}
