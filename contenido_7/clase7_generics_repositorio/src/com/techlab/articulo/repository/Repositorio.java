package com.techlab.articulo.repository;

import java.util.ArrayList;
import java.util.List;

import com.techlab.articulo.interfaces.Identificable;

/*
 * CLASE REPOSITORIO<T>
 * --------------------------------------------------
 * Esta es una clase genérica.
 *
 * La T representa "un tipo cualquiera".
 *
 * Pero no puede ser cualquier cosa totalmente libre:
 * usamos:
 * T extends Identificable
 *
 * Eso significa:
 * "T puede ser cualquier tipo, pero debe implementar Identificable".
 *
 * Entonces este repositorio puede trabajar con:
 * - Articulo
 * - Categoria
 * porque ambos tienen getCodigo()
 *
 * VENTAJA DIDÁCTICA:
 * reutilizamos la misma lógica para distintos tipos.
 */
public class Repositorio<T extends Identificable> {

    private ArrayList<T> lista = new ArrayList<>();

    /*
     * Agrega un objeto al repositorio.
     */
    public void agregar(T objeto) {
        lista.add(objeto);
    }

    /*
     * Devuelve una copia de la lista.
     *
     * Devolvemos una nueva lista para no exponer directamente
     * la estructura interna del repositorio.
     */
    public List<T> listar() {
        return new ArrayList<>(lista);
    }

    /*
     * Busca un objeto por código.
     *
     * Gracias a Identificable, sabemos que todo objeto T
     * tiene el método getCodigo().
     */
    public T buscarPorCodigo(int codigo) {
        for (T objeto : lista) {
            if (objeto.getCodigo() == codigo) {
                return objeto;
            }
        }
        return null;
    }

    /*
     * Elimina un objeto del repositorio.
     */
    public void eliminar(T objeto) {
        lista.remove(objeto);
    }

    /*
     * Indica si el repositorio está vacío.
     */
    public boolean estaVacio() {
        return lista.isEmpty();
    }
}
