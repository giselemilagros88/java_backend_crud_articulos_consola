package com.techlab.articulo.model;

// Importamos Identificable porque la categoría también debe poder
// ser administrada por el repositorio genérico.
import com.techlab.articulo.interfaces.Identificable;

/**
 * Clase que representa una categoría del sistema.
 *
 * Esta clase se usa como objeto dentro de Articulo.
 * Es decir: un artículo no guarda un String como categoría,
 * sino un objeto Categoria completo.
 *
 * Esto es más realista y más cercano a cómo luego se modela
 * una relación entre entidades en backend.
 */
public class Categoria implements Identificable {

    // Código único de la categoría.
    private int codigo;

    // Nombre de la categoría.
    private String nombre;

    // Descripción de la categoría.
    private String descripcion;

    /**
     * Constructor completo.
     *
     * @param codigo código autogenerado.
     * @param nombre nombre de la categoría.
     * @param descripcion descripción de la categoría.
     */
    public Categoria(int codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override
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

    /**
     * Método toString para mostrar la categoría de forma legible.
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
