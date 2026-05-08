package com.techlab.articulo.interfaces;

/*
 * INTERFAZ CALCULABLE
 * --------------------------------------------------
 * Una interfaz define un contrato.
 *
 * ¿Qué significa eso?
 * Significa que cualquier clase que implemente esta interfaz
 * está obligada a escribir este método.
 *
 * En este caso, el contrato dice:
 * "Todo objeto calculable debe saber calcular su precio final".
 *
 * Esto es muy útil porque:
 * - estandariza comportamiento
 * - obliga a distintas clases a implementar el mismo método
 * - permite reforzar polimorfismo
 */
public interface Calculable {

    /*
     * Método abstracto de la interfaz.
     *
     * No lleva cuerpo porque cada clase lo implementará a su manera.
     */
    double calcularPrecioFinal();
}
