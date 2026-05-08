package com.techlab.articulo.model;

/*
 * CLASE ARTICULOELECTRONICO
 * --------------------------------------------------
 * Esta clase implementa calcularPrecioFinal() con una lógica propia.
 *
 * Regla didáctica elegida:
 * - si la garantía supera los 12 meses, se aplica 10% de recargo
 * - si no, el precio final queda igual al precio base
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

    /*
     * Implementación concreta del método de la interfaz.
     *
     * Acá se ve claramente que esta clase cumple el contrato Calculable.
     */
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
