package com.techlab.articulo.model;

/**
 * Clase concreta que representa un artículo alimenticio.
 *
 * Hereda de Articulo y agrega:
 * - días para vencimiento
 *
 * También define su propia lógica de precio final.
 */
public class ArticuloAlimenticio extends Articulo {

    // Cantidad de días restantes para el vencimiento.
    private int diasParaVencimiento;

    /**
     * Constructor completo del artículo alimenticio.
     *
     * @param codigo código autogenerado.
     * @param nombre nombre del artículo.
     * @param precio precio base.
     * @param categoria categoría asociada.
     * @param diasParaVencimiento días para vencimiento.
     */
    public ArticuloAlimenticio(int codigo, String nombre, double precio, Categoria categoria, int diasParaVencimiento) {
        super(codigo, nombre, precio, categoria);
        this.diasParaVencimiento = diasParaVencimiento;
    }

    public int getDiasParaVencimiento() {
        return diasParaVencimiento;
    }

    public void setDiasParaVencimiento(int diasParaVencimiento) {
        this.diasParaVencimiento = diasParaVencimiento;
    }

    @Override
    public String getTipoArticulo() {
        return "Alimenticio";
    }

    @Override
    protected String getDetalleEspecifico() {
        return "días para vencimiento=" + diasParaVencimiento;
    }

    /**
     * Lógica del precio final para alimenticios.
     *
     * Regla didáctica elegida:
     * - si vence en 3 días o menos, se aplica 20% de descuento
     * - si vence en 7 días o menos, se aplica 10% de descuento
     * - si no, se mantiene el precio base
     *
     * Esta lógica permite mostrar que diferentes clases pueden implementar
     * de manera distinta el mismo método de la interfaz.
     */
    @Override
    public double calcularPrecioFinal() {
        if (diasParaVencimiento <= 3) {
            return precio * 0.80;
        }

        if (diasParaVencimiento <= 7) {
            return precio * 0.90;
        }

        return precio;
    }
}
