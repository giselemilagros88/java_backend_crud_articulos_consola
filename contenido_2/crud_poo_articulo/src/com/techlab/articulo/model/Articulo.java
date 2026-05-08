package com.techlab.articulo.model;

/*
 * CLASE ARTICULO
 * ---------------------------------------------------------
 * Esta clase representa el concepto de artículo en el sistema.
 *
 * A diferencia de la clase anterior, ahora no usamos String sueltos.
 * Creamos un tipo de dato propio.
 *
 * Eso nos permite:
 * - agrupar datos relacionados
 * - representar mejor la realidad
 * - reutilizar código
 * - preparar el terreno para POO más avanzada
 */
public class Articulo {

    // Atributo que representa el código del artículo.
    private int codigo;

    // Atributo que representa el nombre del artículo.
    private String nombre;

    // Atributo que representa el precio del artículo.
    private double precio;

    /*
     * CONSTRUCTOR
     * ---------------------------------------------------------
     * El constructor sirve para crear objetos ya inicializados.
     *
     * Cuando hacemos:
     * new Articulo(1, "Mouse", 2500)
     *
     * estamos creando un objeto con estos tres datos cargados.
     */
    public Articulo() {
        // Constructor vacío, permite crear un artículo sin datos iniciales.
    }
    // this es una palabra reservada que hace referencia al objeto actual que estamos instanciado en memoria
    public Articulo(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /*
     * GETTER DE CÓDIGO
     * ---------------------------------------------------------
     * Permite obtener el valor del atributo codigo.
     */
    public int getCodigo() {
        return this.codigo;
    }

    /*
     * SETTER DE CÓDIGO
     * ---------------------------------------------------------
     * Permite modificar el valor del atributo codigo.
     */
    public void setCodigo(int codigo) {
        // validar si el codigo es mayor a 0 para poder setaarlo
      /*  if (codigo <= 0) {
            System.out.println("El código debe ser mayor a 0.");
            return;
            
        }*/ 
        this.codigo = codigo;
    }

    /*
     * GETTER DE NOMBRE
     * ---------------------------------------------------------
     * Permite obtener el nombre del artículo.
     */
    public String getNombre() {
        return this.nombre;
    }

    /*
     * SETTER DE NOMBRE
     * ---------------------------------------------------------
     * Permite modificar el nombre del artículo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*
     * GETTER DE PRECIO
     * ---------------------------------------------------------
     * Permite obtener el precio del artículo.
     */
    public double getPrecio() {
        return this.precio;
    }

    /*
     * SETTER DE PRECIO
     * ---------------------------------------------------------
     * Permite modificar el precio del artículo.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /*
     * MÉTODO toString
     * ---------------------------------------------------------
     * Este método permite definir cómo queremos mostrar el objeto.
     *
     * Si no lo sobrescribimos, Java mostraría algo poco útil, como:
     * com.techlab.articulo.model.Articulo@7a81197d
     *
     * En cambio, con toString() mostramos la información real del objeto.
     *   @Override
    public String toString() {
        return "Artículo {" +
                "código=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
     */
    
  
}
