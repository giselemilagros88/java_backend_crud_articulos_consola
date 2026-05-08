package com.techlab.articulo.model;

/*
 * CLASE ARTICULOALIMENTICIO
 * --------------------------------------------------
 * Esta clase también hereda de Articulo.
 *
 * Agrega como dato propio:
 * - días para vencimiento
 */
public class ArticuloAlimenticio extends Articulo {

    private int diasParaVencimiento;

    public ArticuloAlimenticio(int codigo, String nombre, double precio, Categoria categoria, int diasParaVencimiento) {
        //llamo al constructor de la clase padre para inicializar los atributos heredados
        super(codigo, nombre, precio, categoria);
        this.diasParaVencimiento = diasParaVencimiento;
    }

    public int getDiasParaVencimiento() {
        return diasParaVencimiento;
    }

    public void setDiasParaVencimiento(int diasParaVencimiento) {
        this.diasParaVencimiento = diasParaVencimiento;
    }
     // estos dos methdos son abstractos en la clase padre, por eso los implementamos acá. cada clase hija tiene su propia implementación.
     // y esta obligada a implementarlo. 
    @Override
    public String getTipoArticulo() {
        return "Alimenticio";
    }

    @Override
    public String getDetalleEspecifico() {
        return "Días para vencimiento: " + diasParaVencimiento;
    }

    @Override
    public String toString() {
        return super.toString() + " [subtipo alimenticio]";
    }
}
