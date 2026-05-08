package com.techlab.articulo.model;

/*
 * ARTÍCULO ALIMENTICIO
 * --------------------------------------------------
 * Implementa el cálculo del precio final con descuentos según vencimiento.
 */
public class ArticuloAlimenticio extends Articulo {

    private int diasParaVencimiento;

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
    public String getDetalleEspecifico() {
        return "Días para vencimiento: " + diasParaVencimiento;
    }

    @Override
    public double calcularPrecioFinal() {
        if (diasParaVencimiento <= 3) {
            return getPrecio() * 0.80;
        }

        if (diasParaVencimiento <= 7) {
            return getPrecio() * 0.90;
        }

        return getPrecio();
    }

    @Override
    public String toString() {
        return super.toString() + " [subtipo alimenticio]";
    }
}
