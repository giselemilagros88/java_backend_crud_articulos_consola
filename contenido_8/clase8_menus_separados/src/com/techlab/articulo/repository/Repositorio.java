package com.techlab.articulo.repository;

import java.util.ArrayList;
import java.util.List;

import com.techlab.articulo.interfaces.Identificable;

/*
 * REPOSITORIO GENÉRICO
 * --------------------------------------------------
 * Permite administrar distintos tipos de objetos siempre que
 * implementen Identificable.
 */
public class Repositorio<T extends Identificable> {

    private ArrayList<T> lista = new ArrayList<>();

    public void agregar(T objeto) {
        lista.add(objeto);
    }

    public List<T> listar() {
        return new ArrayList<>(lista);
    }

    public T buscarPorCodigo(int codigo) {
        for (T objeto : lista) {
            if (objeto.getCodigo() == codigo) {
                return objeto;
            }
        }
        return null;
    }

    public void eliminar(T objeto) {
        lista.remove(objeto);
    }

    public boolean estaVacio() {
        return lista.isEmpty();
    }
}
