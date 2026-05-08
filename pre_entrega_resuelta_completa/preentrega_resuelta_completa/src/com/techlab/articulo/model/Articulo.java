package com.techlab.articulo.model;

// Importamos la interfaz Calculable porque todo artículo debe poder
// calcular su precio final según su propia lógica.
import com.techlab.articulo.interfaces.Calculable;

// Importamos la interfaz Identificable porque todo artículo debe poder
// devolver su código para ser administrado por el repositorio genérico.
import com.techlab.articulo.interfaces.Identificable;

/**
 * Clase abstracta que representa la idea general de un artículo.
 *
 * ¿Por qué abstracta?
 * Porque no queremos crear objetos "Artículo" genéricos sin tipo concreto.
 * Queremos obligar a que todo artículo real sea, por ejemplo:
 * - electrónico
 * - alimenticio
 *
 * Esta clase concentra:
 * - atributos comunes
 * - getters y setters comunes
 * - comportamiento común
 *
 * Y deja que las clases hijas definan:
 * - qué tipo de artículo son
 * - cómo calculan su precio final
 * - qué detalle específico tienen
 */
public abstract class Articulo implements Calculable, Identificable {

    // Código único del artículo.
    protected int codigo;

    // Nombre del artículo.
    protected String nombre;

    // Precio base del artículo.
    protected double precio;

    // Categoría asociada al artículo.
    protected Categoria categoria;

    /**
     * Constructor completo de la clase abstracta.
     *
     * @param codigo código autogenerado.
     * @param nombre nombre del artículo.
     * @param precio precio base.
     * @param categoria categoría del artículo.
     */
    public Articulo(int codigo, String nombre, double precio, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
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

    /**
     * Método abstracto que obliga a cada subtipo a indicar qué tipo de artículo es.
     *
     * @return nombre del tipo.
     */
    public abstract String getTipoArticulo();

    /**
     * Método abstracto auxiliar para que cada subtipo informe su detalle específico.
     *
     * Ejemplos:
     * - Electrónico -> garantía
     * - Alimenticio -> días para vencimiento
     *
     * @return detalle específico del subtipo.
     */
    protected abstract String getDetalleEspecifico();

    /**
     * Método toString sobrescrito para mostrar el contenido completo del objeto.
     *
     * ¿Por qué usamos toString?
     * Porque permite que cuando imprimimos el objeto, se vea su información
     * de manera ordenada y legible, sin necesidad de mostrar atributo por atributo
     * en cada parte del programa.
     */
    @Override
    public String toString() {
        return "Artículo {" +
                "código=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio base=" + String.format("%.2f", precio) +
                ", categoría='" + categoria.getNombre() + '\'' +
                ", tipo='" + getTipoArticulo() + '\'' +
                ", " + getDetalleEspecifico() +
                ", precio final=" + String.format("%.2f", calcularPrecioFinal()) +
                '}';
    }
}
