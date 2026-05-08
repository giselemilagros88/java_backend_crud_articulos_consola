package com.techlab.articulo.model;

/*
 * Artículo electrónico con recargo si la garantía supera los 12 meses.
 */
public class ArticuloElectronico extends Articulo {

    private int garantiaMeses;

    public ArticuloElectronico(int codigo, String nombre, double precio, Categoria categoria, int garantiaMeses) {
        super(codigo, nombre, precio, categoria);
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
    public String getDetalleEspecifico() {
        return "Garantía: " + garantiaMeses + " meses";
    }

    @Override
    public double calcularPrecioFinal() {
        if (garantiaMeses > 12) {
            return getPrecio() * 1.10;
        }
        return getPrecio();
    }

    @Override
    public String toString() {
        return super.toString() + " [subtipo electrónico]";
    }
}
