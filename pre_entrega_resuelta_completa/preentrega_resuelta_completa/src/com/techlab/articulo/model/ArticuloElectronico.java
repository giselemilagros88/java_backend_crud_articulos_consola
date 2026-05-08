package com.techlab.articulo.model;

/**
 * Clase concreta que representa un artículo electrónico.
 *
 * Hereda los atributos comunes de Articulo y agrega:
 * - garantía en meses
 *
 * Además, define:
 * - su tipo de artículo
 * - su detalle específico
 * - su propia lógica de precio final
 */
public class ArticuloElectronico extends Articulo {

    // Cantidad de meses de garantía.
    private int garantiaMeses;

    /**
     * Constructor completo del artículo electrónico.
     *
     * @param codigo código autogenerado.
     * @param nombre nombre del artículo.
     * @param precio precio base.
     * @param categoria categoría asociada.
     * @param garantiaMeses garantía en meses.
     */
    public ArticuloElectronico(int codigo, String nombre, double precio, Categoria categoria, int garantiaMeses) {
        // Llamamos al constructor de la superclase para cargar lo común.
        super(codigo, nombre, precio, categoria);

        // Cargamos el atributo propio.
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    @Override
    public String getTipoArticulo() {
        return "Electrónico";
    }

    @Override
    protected String getDetalleEspecifico() {
        return "garantía=" + garantiaMeses + " meses";
    }

    /**
     * Lógica del precio final para artículos electrónicos.
     *
     * Regla didáctica elegida:
     * - si la garantía supera los 12 meses, se aplica un recargo del 10%
     * - si no, el precio final es igual al precio base
     *
     * Esta regla existe para demostrar el uso de la interfaz Calculable.
     */
    @Override
    public double calcularPrecioFinal() {
        if (garantiaMeses > 12) {
            return precio * 1.10;
        }
        return precio;
    }
}
