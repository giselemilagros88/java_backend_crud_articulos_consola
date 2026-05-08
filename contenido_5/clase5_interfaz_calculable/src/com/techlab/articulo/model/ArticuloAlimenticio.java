package com.techlab.articulo.model;

/*
 * CLASE ARTICULOALIMENTICIO
 * --------------------------------------------------
 * Esta clase también implementa calcularPrecioFinal(),
 * pero con otra lógica diferente.
 *
 * Regla didáctica elegida:
 * - si vence en 3 días o menos -> 20% de descuento
 * - si vence en 7 días o menos -> 10% de descuento
 * - si no -> mantiene el precio base
 *
 * Esto demuestra que dos clases distintas pueden implementar
 * el mismo método de manera diferente.
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
