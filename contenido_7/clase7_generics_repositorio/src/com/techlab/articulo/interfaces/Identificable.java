package com.techlab.articulo.interfaces;

/*
 * INTERFAZ IDENTIFICABLE
 * --------------------------------------------------
 * Esta interfaz define un contrato simple:
 * cualquier objeto que quiera ser administrado por el Repositorio<T>
 * debe tener un código.
 *
 * ¿Por qué?
 * Porque el repositorio genérico necesita una regla común para buscar.
 *
 * Entonces, en lugar de saber si trabaja con Articulo o Categoria,
 * simplemente exige que el tipo T tenga el método getCodigo().
 */
public interface Identificable {
    int getCodigo();
}
