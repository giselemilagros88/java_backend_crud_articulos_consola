package com.techlab.articulo.interfaces;

/**
 * Interfaz que obliga a los objetos a tener un código identificador.
 *
 * ¿Para qué sirve?
 * Para que el repositorio genérico pueda trabajar con diferentes tipos
 * de objetos siempre que todos tengan un código.
 *
 * Gracias a esta interfaz, podemos usar:
 * - Repositorio<Categoria>
 * - Repositorio<Articulo>
 *
 * y buscar por código en ambos casos.
 */
public interface Identificable {

    /**
     * Devuelve el código identificador del objeto.
     *
     * @return código del objeto.
     */
    int getCodigo();
}
