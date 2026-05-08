package com.techlab.articulo.interfaces;

/**
 * Interfaz que obliga a implementar el cálculo del precio final.
 *
 * ¿Por qué hacemos esta interfaz?
 * Porque queremos modelar un comportamiento común:
 * todo artículo debe poder calcular su precio final.
 *
 * Sin embargo, cada tipo de artículo puede hacerlo de forma diferente.
 */
public interface Calculable {

    /**
     * Método que devuelve el precio final según la lógica del objeto concreto.
     *
     * @return precio final.
     */
    double calcularPrecioFinal();
}
